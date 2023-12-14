const myChannel = "medical_data_channel";
let pubnub;

const setupPubNub = () => {
    pubnub = new PubNub({
        publishKey: "pub-c-d9563c5e-0407-4a6e-8c69-7fe11c355f4c", // Replace with your actual PubNub publish key
        subscribeKey: "sub-c-8c90cb31-1954-45e8-a659-336e8c6d0668", // Replace with your actual PubNub subscribe key
        uuid: "device id",
    });

    const listener = {
        status: (statusEvent) => {
            if (statusEvent.category === "PNConnectedCategory") {
                console.log("Connected to PubNub");
            }
        },
        message: (messageEvent) => {
            console.log("Received message:", messageEvent.message);
            handleMedicalData(messageEvent.message);
        },
        presence: (presenceEvent) => {
            // handle presence
        },
    };
    pubnub.addListener(listener);

    pubnub.subscribe({
        channels: [myChannel],
    });
};

function sendCommandToRFIDReader(command) {
    pubnub.publish({
        channel: myChannel,
        message: { command },
    });
}

function handleMedicalData(message) {
    document.getElementById("card-info").innerHTML = `Patient ID: ${message.patient_id}<br>Medical History: ${message.medical_data}`;
    document.getElementById("medical-data-display").innerHTML = `Medical Data id: ${message.patient_id}`;
}

// Initialize PubNub once the DOM has loaded
document.addEventListener("DOMContentLoaded", () => {
    setupPubNub();
});