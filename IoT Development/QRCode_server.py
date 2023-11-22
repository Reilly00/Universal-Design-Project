import cv2
import qrcode
import os
import mysql.connector

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
            print("data found:", data)
    
    cv2.imshow("code detector", img)
    
    if cv2.waitKey(1) == ord("q"):
        break

capture.release()
cv2.destroyAllWindows()
