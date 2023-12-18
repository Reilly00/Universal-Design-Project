package com.example.careconnect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController? = null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var passwordRequirementError by remember { mutableStateOf("") }

    // Additional state for password strength
    val passwordStrength = getPasswordStrength(password)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Contact profile picture",
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(80.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() },
            label = { Text("Email") },
            singleLine = true,
            isError = emailError.isNotEmpty()
        )

        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it.trim()
            },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError || passwordRequirementError.isNotEmpty()
        )

        // Display Password Strength Meter
        PasswordStrengthMeter(strength = passwordStrength)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it.trim() },
            label = { Text("Confirm Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError
        )

        if (passwordError) {
            Text(
                text = "Passwords do not match",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        if (passwordRequirementError.isNotEmpty()) {
            Text(
                text = passwordRequirementError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                emailError = if (!isEmailValid(email)) "Invalid email format" else ""
                passwordError = password != confirmPassword
                if (passwordError) {
                    passwordRequirementError = ""
                } else if (!isPasswordValid(password)) {
                    passwordRequirementError = "Password must:\n Be at least 8 characters, \n include an uppercase letter, \n a lowercase letter, symbol, and a number."
                } else {
                    passwordRequirementError = ""
                    // TODO: Add registration logic here if passwords match and meet requirements
                }
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Already have an account? Log-in",
            modifier = Modifier.clickable { navController?.navigate("login") }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun PasswordStrengthMeter(strength: PasswordStrength) {
    val strengthColor = when (strength) {
        PasswordStrength.STRONG -> Color.Green // Strong password
        PasswordStrength.MEDIUM -> Color(0xFFFFA500) // Medium strength - Orange color
        PasswordStrength.WEAK -> Color.Red // Weak password
        PasswordStrength.NONE -> Color.Gray // No password entered
    }

    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Password Strength: ", style = MaterialTheme.typography.bodySmall)
        Box(modifier = Modifier
            .width(100.dp)
            .height(10.dp)
            .background(strengthColor))
    }
}
