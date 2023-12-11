
// /iotProjects/CardReader/static/login.js

function loginUser() {
    const loginUsername = document.getElementById("login-username").value;
    const loginPassword = document.getElementById("login-password").value;

    // Add logic to check credentials (replace with actual implementation)
    if (loginUsername === "your_username" && loginPassword === "your_password") {
        // Redirect to the main page if login is successful
        window.location.href = "/iotProjects/CardReader/templates/index.html";
    } else {
        // Display login error
        document.getElementById("login-error").innerHTML = "Invalid credentials";
    }
}
