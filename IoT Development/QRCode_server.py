
import json
import os
import mysql.connector
from PIL import Image
import qrcode
from zbarlight import scan_codes
from picamera import PiCamera
from picamera.array import PiRGBArray
from time import sleep
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub
from pubnub.callbacks import SubscribeCallback
from pubnub.enums import PNStatusCategory

# === Define QRcode generator function ===
def generate_qrcode_with_id(patient_id):
    data = f"PatientID:{patient_id}"
    
    # === Create folder to save QRCode pictures ===
    if not os.path.exists("qrcodes"):
        os.makedirs("qrcodes")
    
    qrCode = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=5,
    )
    
    qrCode.add_data(data)
    qrCode.make(fit=True)
    img = qrCode.make_image(fill_color="black", back_color="white")
    img_path = f"qrcodes/{patient_id}.png"
    img.save(img_path)
    return img_path


#=== Connect DataBase and Assign a QRcode for each Id ===
def get_patient_data(connection, patient_id):
    try:
        if connection.is_connected():
            cursor = connection.cursor(dictionary=True)
            query = f"SELECT * FROM Patients WHERE PatientID = {patient_id}"
            cursor.execute(query)
            result = cursor.fetchone()
            return result
    except mysql.connector.Error as e:
        print(f"Error connecting to the database: {e}")


def detect_qrcode(image_path):
    with open(image_path, 'rb') as image_file:
        image = Image.open(image_file)
        codes = scan_codes(['qrcode'], image)
        return codes[0].decode('utf-8') if codes else None

# === PubNub Configuration ===
pnconfig = PNConfiguration()
pnconfig.subscribe_key = " " # === personal subscribe key from PubNub ===
pnconfig.publish_key = " "  # === personal publish key from PubNub ===
pnconfig.user_id = "erling"  
pubnub = PubNub(pnconfig)

# === Subscribe Callback for PubNub ===
class MySubscribeCallback(SubscribeCallback):
    def presence(self, pubnub, presence):
        pass

    def status(self, pubnub, status):
        print(f"Status Category: {status.category}")
        if status.category == PNStatusCategory.PNUnexpectedDisconnectCategory:
            pass

        elif status.category == PNStatusCategory.PNConnectedCategory:
            print("Connected to PubNub")
            
        elif status.category == PNStatusCategory.PNReconnectedCategory:
            pass

        elif status.category == PNStatusCategory.PNDecryptionErrorCategory:
            pass

    def message(self, pubnub, message):
        print(message.message)

pubnub.add_listener(MySubscribeCallback())
pubnub.subscribe().channels("careconnect").execute() 

# === Handle publish result ===
def handle_publish(result, status):
    if not status.is_error():
        print("Message published successfully")
    else:
        print(f"Error publishing message: {status.error_data}")
# === Connect to the database ===
try:
    connection = mysql.connector.connect(
        host="localhost",
        user="root",
        password=" ", # === personal password ===
        database="patient_management",
    )
    
    # === Check if the connection is successful ===
    if connection.is_connected():
        print("Connected to MySQL")
        
        cursor = connection.cursor()
        cursor.execute("SELECT PatientID FROM Patients")
        patient_ids = [row[0] for row in cursor.fetchall()]

        # === Test the QR code generation with a specific patient ID ===
        for patient_id in patient_ids:
            generated_path = generate_qrcode_with_id(patient_id)
            print(f"QR Code for Patient ID {patient_id} generated and saved at: {generated_path}")

        # === set up PiCamera ===
        camera = PiCamera()
        camera.resolution = (640, 480)
        raw_capture = PiRGBArray(camera, size=(640, 480))

        sleep(2)

        # === Continuously capture images from the camera ===
        for frame in camera.capture_continuous(raw_capture, format="bgr", use_video_port=True):
            image = frame.array

            # === Save the captured image ===
            image_path = "qrcodes/captured_image.png"
            Image.fromarray(image).save(image_path)

            # === detect QR code ===
            qr_data = detect_qrcode(image_path)

            if qr_data:
                # === Extract the patient ID from the data ===
                _, patient_id_str = qr_data.split(":")
                try:
                    patient_id = int(patient_id_str)

                    # === Retrieve patient information from the database ===
                    patient_data = get_patient_data(connection, patient_id)

                    if patient_data:
                        print("Patient ID:", patient_id)
                        print("Patient Information:", patient_data)

                        # === Publish patient data to PubNub ===
                        pubnub.publish().channel("careconnect").message(patient_data).pn_async(lambda result, status: handle_publish(result, status))
                    else:
                        print("Patient not found")

                except ValueError:
                    print("Invalid patient ID format")

            # === Clear the stream in preparation for the next frame ===
            raw_capture.truncate(0)

except KeyboardInterrupt:
    print("\nProgram interrupted")
finally:
    if 'connection' in locals() and connection.is_connected():
        connection.close()
        print("MySQL connection closed.")