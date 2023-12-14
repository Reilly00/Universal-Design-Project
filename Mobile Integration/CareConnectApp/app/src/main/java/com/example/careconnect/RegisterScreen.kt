package com.example.careconnect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController? = null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var passwordRequirementError by remember { mutableStateOf("") }
    var registrationStatus by remember { mutableStateOf<RegistrationStatus>(RegistrationStatus.NONE) }
    var isRegistrationInProgress by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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
            contentDescription = "Care Connect Logo",
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(128.dp))

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
            onValueChange = { password = it.trim() },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError || passwordRequirementError.isNotEmpty()
        )

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
                passwordRequirementError = if (!isPasswordValid(password)) {
                    "Password must: \nBe at least 8 characters, \ninclude an uppercase letter, \nlowercase letter, symbol, and a number."
                } else ""

                if (emailError.isEmpty() && !passwordError && passwordRequirementError.isEmpty() && !isRegistrationInProgress) {
                    isRegistrationInProgress = true
                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.instance.registerUser(
                                RegistrationData(username = email, email = email, password = password)
                            )
                            if (response.isSuccessful) {
                                registrationStatus = RegistrationStatus.SUCCESS
                                navController?.navigate("dashboard")
                            } else {
                                registrationStatus = RegistrationStatus.ERROR
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            registrationStatus = RegistrationStatus.ERROR
                        } finally {
                            isRegistrationInProgress = false
                        }
                    }
                }
            },
            enabled = !isRegistrationInProgress,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Register")
        }

        when (registrationStatus) {
            RegistrationStatus.SUCCESS -> Text("Registration successful", color = Color.Green)
            RegistrationStatus.ERROR -> Text("Registration failed", color = Color.Red)
            else -> {} // Do nothing for NONE
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Already have an account? Log in",
            modifier = Modifier.clickable { navController?.navigate("login") }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun PasswordStrengthMeter(strength: PasswordStrength) {
    val strengthColor = when (strength) {
        PasswordStrength.STRONG -> Color.Green
        PasswordStrength.MEDIUM -> Color(0xFFFFA500)
        PasswordStrength.WEAK -> Color.Red
        PasswordStrength.NONE -> Color.Gray
    }

    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Password Strength: ", style = MaterialTheme.typography.bodySmall)
        Box(modifier = Modifier
            .width(100.dp)
            .height(10.dp)
            .background(strengthColor))
    }
}

enum class RegistrationStatus {
    NONE, SUCCESS, ERROR
}