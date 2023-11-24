import qrcode

def generate_qr_code(data):
    # Create a QR code instance
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    
    # Add data to the QR code
    qr.add_data(data)
    qr.make(fit=True)
    
    # Create an image from the QR code instance
    img = qr.make_image(fill_color="black", back_color="white")
    
    # Save the image
    img.save("/home/abielinsky/iotProjects/CardReader/qr_code.png")
    print("QR code generated successfully!")

def get_personal_data():
    name = input("Enter your name: ")
    age = input("Enter your age: ")
    gender = input("Enter your gender: ")
    medical_description = input("Enter your medical description: ")
    
    # Format personal data as a string
    personal_data = f"Name: {name}\nAge: {age}\nGender: {gender}\nMedical Description: {medical_description}"
    
    return personal_data

if __name__ == "__main__":
    # Get personal data from user input
    data = get_personal_data()
    
    # Generate QR code
    generate_qr_code(data)
