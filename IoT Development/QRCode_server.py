import cv2
import qrcode
import os
import mysql.connector
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub
from pubnub.callbacks import SubscribeCallback
from pubnub.enums import PNStatusCategory, PNOperationType

# === Define QRcode generator function ===
def generate_QRcode_WithId(patient_id):
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
        

# === PubNub Configuration ===
pnconfig = PNConfiguration()
pnconfig.subscribe_key = " " # Personal subscribe_key from PubNub 
pnconfig.publish_key = " " # Personal publish_key from PubNub 
pnconfig._uuid = "erling"  
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



def handle_publish(result, status):
    if not status.is_error():
        print("Message published successfully")
    else:
        print(f"Error publishing message: {status.error_data}")

        
try:           
    connection = mysql.connector.connect(
        host="localhost",
        user="root",
        password="",
        database="patient_management"
    )

    if connection.is_connected():
        cursor = connection.cursor()
        cursor.execute("SELECT PatientID FROM Patients")
        patient_ids = [row[0] for row in cursor.fetchall()]

 
    # === Test the QR code generation with a specific patient ID ===
        for patient_id in patient_ids:
            generated_path = generate_QRcode_WithId(patient_id)
            print(f"QR Code for Patient ID {patient_id} generated and saved at: {generated_path}")

        # === set up camera ===
        capture = cv2.VideoCapture(0)

        # === QR code detection ===
        detector = cv2.QRCodeDetector()

        while True:
            _, img = capture.read()
    
        # === bounding box coordenates and data ===
            data, bbox, _ = detector.detectAndDecode(img)
    
    # === Bounding  a box if not found it, draw one, and then, display the data ===
            if bbox is not None:
                for i in range(len(bbox)):
                    point1 = (int(bbox[i][0][0]), int(bbox[i][0][1]))
                    point2 = (int(bbox[(i+1) % len(bbox)][0][0]), int(bbox[(i+1) % len(bbox)][0][1]))
                    cv2.line(img, point1, point2, color=(255, 0, 255), thickness=2)
            
                cv2.putText(img, data, (int(bbox[0][0][0]), int(bbox[0][0][1]) - 10), cv2.FONT_HERSHEY_SIMPLEX,
                    0.5, (0, 255, 0), 2)
        
                if data:
                    #  === Extract the patient ID from the data ===
                    _, patient_id_str = data.split(":")
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

            cv2.imshow("Code Detector", img)

            if cv2.waitKey(1) == ord("q"):
                break
finally:
    if connection.is_connected():
        connection.close()
        
    capture.release()
    cv2.destroyAllWindows()
