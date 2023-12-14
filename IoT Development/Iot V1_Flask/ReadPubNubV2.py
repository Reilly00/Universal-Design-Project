import sqlite3
import uuid
from mfrc522 import SimpleMFRC522
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub
from dotenv import load_dotenv
import os
import time
import RPi.GPIO as GPIO

load_dotenv()

# Get PubNub keys from the environment variables
pubnub_subscribe_key = os.getenv("PUBNUB_SUBSCRIBE_KEY")
pubnub_publish_key = os.getenv("PUBNUB_PUBLISH_KEY")

# PubNub Configuration
pnconfig = PNConfiguration()
pnconfig.subscribe_key = pubnub_subscribe_key
pnconfig.publish_key = pubnub_publish_key
pnconfig.uuid = str(uuid.uuid4())
pnconfig.ssl = False

pubnub = PubNub(pnconfig)

# SQLite Connection
conn = sqlite3.connect('medical_data_channel_DB.db')
cursor = conn.cursor()

# Ensure the 'cards' table structure matches the INSERT statement
# CREATE TABLE IF NOT EXISTS cards (
#     id INTEGER PRIMARY KEY AUTOINCREMENT,
#     patient_id TEXT NOT NULL,
#     medical_data TEXT NOT NULL
# );


def publish_to_pubnub(patient_id, medical_data):
    channel = "medical_data_channel"

    message = {
        "patient_id": patient_id,
        "medical_data": medical_data
    }

    try:
        # Publish to PubNub
        pubnub.publish().channel(channel).message(message).sync()
        print("Medical data published to PubNub.")

        # Insert data into SQLite tables
        cursor.execute(
            "INSERT INTO cards (patient_id, medical_data) VALUES (?, ?)", (patient_id, medical_data))
        conn.commit()

    except Exception as e:
        print(f"Error: {e}")
        conn.rollback()  # Rollback the transaction in case of an error


try:
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)

    reader = SimpleMFRC522()

    while True:
        id, text = reader.read()
        print("Patient ID:", id)
        print("Medical Data:", text)

        # Publish to PubNub and insert into SQLite tables
        publish_to_pubnub(id, text)

        # Wait for a brief period before attempting to read the next card
        time.sleep(2)  # Adjust the sleep duration as needed

except KeyboardInterrupt:
    pass  # Allow the script to be interrupted by pressing Ctrl+C

finally:
    GPIO.cleanup()
    conn.close()  # Close SQLite connection when done
