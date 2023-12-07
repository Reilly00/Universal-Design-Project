package com.example.careconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.careconnect.ui.theme.CareConnectTheme

// https://developer.android.com/reference/kotlin/androidx/compose/ui/platform/SoftwareKeyboardController

@Composable
fun EmailScreen(navController: NavController? = null) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        EmailContent()
        navController?.let { BottomNavigationBar(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailContent() {
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Subject field
        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Email Body
        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Body") },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(bottom = 8.dp)
        )

        // Send button
        SendButton(
            onSendClick = {
                // Handle sending the email
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SendButton(onSendClick: () -> Unit) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var isKeyboardVisible by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        val viewTreeObserver = (context as ComponentActivity).window.decorView.viewTreeObserver
        val preDrawListener = android.view.ViewTreeObserver.OnPreDrawListener {
            isKeyboardVisible = context.window.decorView.rootView.height - context.window.decorView.bottom > 100
            if (isKeyboardVisible) {
                keyboardController?.hide()
            }
            true
        }

        viewTreeObserver.addOnPreDrawListener(preDrawListener)
        onDispose {
            viewTreeObserver.removeOnPreDrawListener(preDrawListener)
        }
    }

    // Send button
    Button(
        onClick = onSendClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text("Send", color = Color.White)
    }
}