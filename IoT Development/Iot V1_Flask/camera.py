import time
import picamera

def capture_and_display_image():
    with picamera.PiCamera() as camera:
        # Set camera resolution
        camera.resolution = (640, 480)

        # Wait for the camera to warm up
        time.sleep(2)

        # Capture an image
        image_path = 'captured_image.jpg'
        camera.capture(image_path)
        print(f"Image captured and saved to {image_path}")

if __name__ == "__main__":
    print("Capturing an image. Please wait...")
    capture_and_display_image()
    print("Image captured successfully.")
