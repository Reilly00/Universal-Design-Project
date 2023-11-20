#!/usr/bin/env python3
from flask import Flask, render_template, Response
import sys
print(sys.executable)
from pyzbar.pyzbar import decode
import cv2 

app = Flask(__name__)

def scan_barcode(frame):
    decoded_objects = decode(frame)
    for obj in decoded_objects:
        print("Data:", obj.data.decode('utf-8'))
        # Add logic to retrieve corresponding data from your dataset
    return decoded_objects

def generate_frames():
    cap = cv2.VideoCapture(0)
    while True:
        ret, frame = cap.read()
        decoded_objects = scan_barcode(frame)

        ret, jpeg = cv2.imencode('.jpg', frame)
        frame_bytes = jpeg.tobytes()
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame_bytes + b'\r\n\r\n')

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/video_feed')
def video_feed():
    return Response(generate_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')

if __name__ == '__main__':
   app.run(host="172.20.10.7", port=5000)


