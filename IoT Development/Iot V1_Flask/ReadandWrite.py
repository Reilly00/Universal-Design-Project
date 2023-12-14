import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522

def write_data_to_card():
    try:
        print("Scan the card to write medical data.")
        id, _ = reader.read()

        historical_data = input("Enter historical medical data for the patient: ")

        data_to_write = f"Patient ID: {id}\nMedical History: {historical_data}"

        print("Writing data to the card tap again... ")
        reader.write(data_to_write)
        print("Data written successfully.")

    finally:
        GPIO.cleanup()

if __name__ == "__main__":
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)
    reader = SimpleMFRC522()

    try:
        while True:
            action = input("Choose an action (1: Read, 2: Write, q: Quit): ")

            if action == "1":
                id, text = reader.read()
                print("Patient ID:", id)
                print("Medical Data:", text)

            elif action == "2":
                write_data_to_card()

            elif action.lower() == "q":
                break

            else:
                print("Invalid action. Please choose again.")

    finally:
        GPIO.cleanup()

