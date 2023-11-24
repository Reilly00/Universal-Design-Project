import os
import uuid
import pymysql
import logging
from flask import Flask
from dotenv import load_dotenv
from pubnub.pubnub import PubNub, SubscribeCallback
from pubnub.pnconfiguration import PNConfiguration

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

def insert_patient_data(patient_data):
    connection = pymysql.connect(**db_config)
    try:
        with connection.cursor() as cursor:
            sql = "INSERT INTO Patients (PatientID, CaregiverID, FirstName, LastName, Address, PhoneNumber, ClinicalHistory) VALUES (%s, %s, %s, %s, %s, %s, %s)"
            cursor.execute(sql, (
                patient_data['PatientID'],
                patient_data['CaregiverID'],
                patient_data['FirstName'],
                patient_data['LastName'],
                patient_data['Address'],
                patient_data['PhoneNumber'],
                patient_data['ClinicalHistory']
            ))
        connection.commit()
    finally:
        connection.close()

logging.basicConfig(filename='app.log', level=logging.INFO)

class MySubscribeCallback(SubscribeCallback):
    def message(self, pubnub, message):
        logging.info(f"Received message: {message.message}")
        print("Received PubNub message:", message.message)
        insert_patient_data(message.message)

pubnub.add_listener(MySubscribeCallback())
pubnub.subscribe().channels('careconnect').execute()

@app.route('/')
def index():
    return "Connected to the database!"

@app.route('/users')
def list_users():
    # Implement logic to retrieve and display users if needed
    return "User listing endpoint"

if __name__ == '__main__':
    app.run(debug=True)