# RFID RC522 Setup on Raspberry Pi

## Introduction

Before configuring the RFID Reader on your Raspberry Pi, it's crucial to make adjustments to its configuration. The default setting disables SPI, and this poses a challenge since the RFID reader circuit relies on it.

## Step 1: Configuring SPI

To initiate the setup, open the `raspi-config` tool by executing the following command in the terminal:

```bash
sudo raspi-config

