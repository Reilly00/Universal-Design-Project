import os
import uuid
import logging
import pymysql
import bcrypt
from flask import Flask, request, jsonify
from dotenv import load_dotenv
from pubnub.pubnub import PubNub, SubscribeCallback
from pubnub.pnconfiguration import PNConfiguration

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

# PubNub configuration
pnconfig = PNConfiguration()
pnconfig.subscribe_key = os.environ.get('sub-c-397c95e0-69bd-460b-be3a-76b966f22ac7')
pnconfig.publish_key = os.environ.get('pub-c-722f5c5e-5048-4c97-b20e-1b9e16eb6444')
pnconfig.uuid = str(uuid.uuid4())
pnconfig.ssl = True

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
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:  # Use DictCursor
            sql = "SELECT * FROM users WHERE username = %s"
            cursor.execute(sql, (username,))
            return cursor.fetchone()
    finally:
        connection.close()

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
            "profile_pic_url": user.get('profile_pic_url')  # Add this line
        }), 200
    else:
        return jsonify({"message": "Invalid username or password"}), 401

# PubNub subscription callback
class MySubscribeCallback(SubscribeCallback):
    def message(self, pubnub, message):
        logging.info(f"Received message: {message.message}")
        print("Received PubNub message:", message.message)
        # Add logic for handling incoming PubNub messages
        # For example, inserting patient data into the database

if __name__ == '__main__':
    logging.basicConfig(filename='app.log', level=logging.INFO)
    pubnub.add_listener(MySubscribeCallback())
    pubnub.subscribe().channels('careconnect').execute()
    app.run(debug=True)