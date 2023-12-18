import os
import uuid
import logging
import pymysql
import bcrypt
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub, SubscribeCallback
from dotenv import load_dotenv
from flask import Flask, request, jsonify

# Set up logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# Load environment variables
load_dotenv()

app = Flask(__name__)

# Database configuration
db_config = {
    'host': os.environ.get('DB_ENDPOINT', 'default_endpoint'),
    'user': os.environ.get('DB_USERNAME', 'default_username'),
    'password': os.environ.get('DB_PASSWORD', 'default_password'),
    'database': os.environ.get('DB_NAME', 'default_dbname'),
    'port': int(os.environ.get('DB_PORT', 3306)),
}

# Get PubNub keys from the environment variables
pubnub_subscribe_key = os.getenv("PUBNUB_SUBSCRIBE_KEY")
pubnub_publish_key = os.getenv("PUBNUB_PUBLISH_KEY")

pnconfig = PNConfiguration()
pnconfig.subscribe_key = pubnub_subscribe_key
pnconfig.publish_key = pubnub_publish_key
pnconfig.uuid = str(uuid.uuid4())
pnconfig.ssl = False

pubnub = PubNub(pnconfig)

# Database helper functions
def hash_password(password):
    return bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

def verify_password(plain_text_password, hashed_password):
    return bcrypt.checkpw(plain_text_password.encode('utf-8'), hashed_password.encode('utf-8'))

def insert_user(username, email, password_hash):
    connection = pymysql.connect(**db_config)
    try:
        with connection.cursor() as cursor:
            sql = "INSERT INTO users (username, email, password_hash) VALUES (%s, %s, %s)"
            cursor.execute(sql, (username, email, password_hash))
        connection.commit()
    finally:
        connection.close()

def get_user(username):
    connection = pymysql.connect(**db_config)
    try:
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            sql = "SELECT * FROM users WHERE username = %s"
            cursor.execute(sql, (username,))
            return cursor.fetchone()
    finally:
        connection.close()

def get_patient_cards_for_user(user_id):
    connection = pymysql.connect(**db_config)
    try:
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            sql = """
            SELECT cards.id, cards.patient_id, cards.medical_data
            FROM cards
            JOIN users ON cards.user_id = users.user_id
            WHERE users.user_id = %s
            """
            cursor.execute(sql, (user_id,))
            return cursor.fetchall()
    finally:
        connection.close()

# PubNub subscribe callback
class MySubscribeCallback(SubscribeCallback):
    def message(self, pubnub, message):
        logging.info(f"Received message: {message.message}")
        patient_id = message.message.get('patient_id')
        medical_data = message.message.get('medical_data')
        
        # Process the received data (e.g., insert into database)
        try:
            with pymysql.connect(**db_config) as connection:
                with connection.cursor() as cursor:
                    sql = "INSERT INTO cards (patient_id, medical_data) VALUES (%s, %s)"
                    cursor.execute(sql, (patient_id, medical_data))
                connection.commit()
                logging.info("Data saved to database")
        except Exception as e:
            logging.error(f"Error in saving to database: {e}")

# Add PubNub listener and subscribe to the channel
pubnub.add_listener(MySubscribeCallback())
pubnub.subscribe().channels("medical_data_channel").execute()

# Flask routes
@app.route('/')
def index():
    return "Connected to the database!"

@app.route('/register', methods=['POST'])
def register():
    data = request.json
    username = data['username']
    email = data['email']
    password = data['password']

    hashed_password = hash_password(password)
    insert_user(username, email, hashed_password)

    return jsonify({"message": "User registered successfully"}), 201

@app.route('/login', methods=['POST'])
def login():
    data = request.json
    username = data['username']
    password = data['password']

    user = get_user(username)
    if user and verify_password(password, user['password_hash']):
        return jsonify({
            "message": "Login successful",
            "profile_pic_url": user.get('profile_pic_url'),
            "user_id": user['user_id'] 
        }), 200
    else:
        return jsonify({"message": "Invalid username or password"}), 401

@app.route('/user/<int:user_id>/patient_cards', methods=['GET'])
def patient_cards(user_id):
    try:
        patient_cards = get_patient_cards_for_user(user_id)
        return jsonify(patient_cards), 200
    except Exception as e:
        logging.error(f"Error in retrieving patient cards: {e}")
        return jsonify({"message": "Error in retrieving patient cards"}), 500

if __name__ == '__main__':
    try:
        app.run(host='0.0.0.0', port=5000)
    except KeyboardInterrupt:
        logging.info("Shutting down gracefully...")
        # Unsubscribe and stop PubNub instance
        pubnub.unsubscribe().channels("medical_data_channel").execute()
        pubnub.stop()
        logging.info("PubNub unsubscribed and stopped.")
        # Add any other cleanup code here if necessary
        logging.info("Application stopped.")