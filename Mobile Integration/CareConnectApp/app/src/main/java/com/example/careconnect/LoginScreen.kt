package com.example.careconnect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController? = null, userViewModel: UserViewModel) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginStatus by remember { mutableStateOf(LoginStatus.NONE) }
    var isLoginInProgress by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val lightPinkColor = Color(0xFFF5F1F2)
    val strongerPinkColor = Color(0xFF947B83)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(lightPinkColor, strongerPinkColor),
                    startY = -1.5f,
                    endY = 2800f
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(128.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it.trim() },
            label = { Text("Email", color = Color.Black) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it.trim() },
            label = { Text("Password", color = Color.Black) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (!isLoginInProgress) {
                    isLoginInProgress = true
                    coroutineScope.launch {
                        var attempts = 0
                        val maxAttempts = 10
                        while (attempts < maxAttempts) {
                            try {
                                val response = RetrofitClient.instance.loginUser(LoginData(username, password))
                                if (response.isSuccessful && response.body() != null) {
                                    val loginResponse = response.body()!!
                                    userViewModel.updateProfilePicUrl(loginResponse.profile_pic_url)
                                    userViewModel.updateUserId(4)
                                    navController?.navigate("dashboard")
                                    loginStatus = LoginStatus.SUCCESS
                                    break
                                } else {
                                    attempts++
                                    loginStatus = if (attempts >= maxAttempts) LoginStatus.ERROR else LoginStatus.NONE
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                attempts++
                                loginStatus = if (attempts >= maxAttempts) LoginStatus.ERROR else LoginStatus.NONE
                            }
                        }
                        isLoginInProgress = false
                    }
                }
            },
            enabled = !isLoginInProgress,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Login")
        }

        when (loginStatus) {
            LoginStatus.SUCCESS -> Text("Login successful")
            LoginStatus.ERROR -> Text("Login failed", color = Color.Red)
            else -> {} // Do nothing for NONE
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Don't have an account? Register here",
            color = Color.Black, // Set text color
            modifier = Modifier.clickable { navController?.navigate("register") }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

enum class LoginStatus {
    NONE, SUCCESS, ERROR
}