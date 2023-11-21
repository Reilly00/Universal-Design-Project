import RPi.GPIO as GPIO
import uuid
from mfrc522 import SimpleMFRC522
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub
from dotenv import load_dotenv
import os
import time

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

def publish_to_pubnub(patient_id, medical_data):
    channel = "medical_data_channel"

    message = {
        "patient_id": patient_id,
        "medical_data": medical_data
    }

    pubnub.publish().channel(channel).message(message).sync()
    print("Medical data published to PubNub.")

try:
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)
    
    reader = SimpleMFRC522()

    while True:
        id, text = reader.read()
        print("Patient ID:", id)
        print("Medical Data:", text)

        # Publish to PubNub
        publish_to_pubnub(id, text)

        # Wait for a brief period before attempting to read the next card
        time.sleep(2)  # Adjust the sleep duration as needed

except KeyboardInterrupt:
    pass  # Allow the script to be interrupted by pressing Ctrl+C

finally:
    GPIO.cleanup()




