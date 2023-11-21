# <a href="#" class="button">"CARE CONNECT, IOT PROTOTYPE"</a> [![Button 1](https://img.shields.io/badge/%22CARE-CONNECT%22-purple.svg)](https://example.com/button1 "Tooltip Text") <a href="https://www.dkit.ie/" class="button"> </a>

### Purpose of the IoT Prototype
The Care Connect IoT Prototype serves as a beacon of progress in dementia care. 
By incorporating RFID technology, it enables the retrieval of historical medical data from patient 
cards or tags in real-time. This capability streamlines communication and data sharing, creating a 
comprehensive and user-friendly tool for enhancing the caregiving experience.

### Key Features
Real-time Data Retrieval: The IoT prototype employs the RFID RC522 setup on a Raspberry 
Pi to retrieve historical medical data instantly from patients with dementia, providing caregivers and medical 
professionals as a Doctors with up-to-date information.

### Seamless Communication:
Care Connect fosters a network of seamless communication, 
connecting caregivers and medical professionals through a user-friendly digital platform.

### Enhanced Care Coordination: 
Breaking away from traditional fragmented methods, our prototype promotes enhanced care 
coordination by centralizing critical patient information.

## <a href="#" class="button big">Architecture of the Project:</a> [![Button 1](https://img.shields.io/badge/%22Diagram_1%22-purple.svg)](https://example.com/button1 "Tooltip Text")

![Php, Html, Sql](https://raw.githubusercontent.com/Reilly00/Universal-Design-Project/main/IoT%20Development/arquitecture.jpg)


## <a href="#" class="button big">Card Reader Diagram:</a> [![Button 1](https://img.shields.io/badge/%22Diagram_2%22-purple.svg)](https://example.com/button1 "Tooltip Text")

![Php, Html, Sql](https://raw.githubusercontent.com/Reilly00/Universal-Design-Project/main/IoT%20Development/CardReaderDiagram.jpg)

### Overview
The card reader diagram illustrates the flow of data and interactions within the system 
related to card reading functionality. This functionality is a crucial component of our
application/repository, facilitating the reading and processing of card data.


## Prototype Components (Building a Prototype)
1. User Interface (UI):

Represents the graphical interface through which caregivers and medical professionals interact with the Care Connect IoT Prototype.
Initiates and monitors the RFID data retrieval process.

2. RFID RC522 on Raspberry Pi:

Physical setup leveraging RFID technology for reading patient cards or tags.
Interfaces with the application to transmit RFID data.

3. Data Processing Module:

Backend module responsible for processing the RFID data received from the RFID RC522.
Validates and interprets the RFID data to extract historical medical information.

4. Database:

Centralized database storing historical medical data retrieved from patient cards or tags.
Accessed by the application for storing and retrieving patient information.

### Security Measures

Ensuring data security is a top priority in the Care Connect architecture.

* Access Controls: Strict access controls are implemented for cloud services, 
limiting data access to authorized personnel only.

* Secure Data Storage: Patient data is securely stored on the cloud, 
with adherence to industry standards for data protection.


## <a href="#" class="button big">Setting up Raspberry for RFID</a> [![Button 1](https://img.shields.io/badge/%22Usage%22-red.svg)](https://example.com/button1 "Tooltip Text")

Before configuring the RFID Reader on our Raspberry Pi, we must adjunst its configuration. By default,
the Raspberry Pi disables SPI, and this poses a challenge since our RFID reader circuit depends on it.<br>

### Step 1: 
To start, let's open the raspi-config tool. yyou achieve this by lauching the terminal and executing
the next command.

```
pi@raspberrypi: sudo raspi-config
```
This command will execute the Raspberry Pi Software Configuration Tool from the Raspberry Pi.

We go now to the option 5, Interfacing Option and press Enter, it will open a new window 
where we Enable the option SPI "P4 SPI".

We press yes to enable it, then ok and Enter to save changes.

We proceed to reboot.

```
pi@raspberrypi: sudo reboot
```

### Step 2:

We print a list of the active kernel list mods using the next command:

```
pi@raspberrypi: lsmod | grep spi
```
If the kernel list shows “spi_bcm2535” and “spidev” appears then the SPI 
interface has been successfully setup.

### Step 2:

Packages to install:

```
pi@raspberrypi: sudo apt-get update
pi@raspberrypi: sudo apt-get upgrade
pi@raspberrypi: sudo apt-get install python3-dev python3-pip
pi@raspberrypi: sudo pip3 install spidev
pi@raspberrypi: sudo pip3 install mfrc522

```



## <a href="#" class="button big"></a> [![Button 1](https://img.shields.io/badge/%22Conclusion%22-blue.svg)](https://example.com/button1 "Tooltip Text")

In bringing the Care Connect project to a close, we've revolutionized dementia care 
through a user-friendly digital platform. Our innovative IoT prototype, 
fueled by the power of PubNub for real-time communication and Amazon Web Services 
(AWS) for secure and scalable data storage, seeks to simplify the caregiver's journey
and enhance the quality of care.

Explore the attached code to delve into the core of Care Connect. Your contributions and insights 
play a vital role in shaping the future of our project. Together, let's make a meaningful impact on dementia care. Thank you for being an essential part of the Care Connect community!

## Files

```
Read.py
Write.py 
```
* Write a file, that will configure each  "RFID card" or "RFID tag".
* Read file, will read data from each patient.


[![Button 1](https://img.shields.io/badge/Care-Connect%20-Purple.svg)](https://example.com/button1 "Tooltip Text")
