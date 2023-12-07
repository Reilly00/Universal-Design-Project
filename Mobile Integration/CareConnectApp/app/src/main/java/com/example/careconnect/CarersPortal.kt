package com.example.careconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardOptions.Companion.Default
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.careconnect.ui.theme.CareConnectTheme

@Composable
fun CarersPortal() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MessagingContent()
        BottomNavigationBar()
    }
}

@Composable
fun MessagingContent() {
    var message by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display messages
        MessageList(messages = messages)

        // Input for typing messages
        MessageInput()
    }
}

@Composable
fun MessageList(messages: List<String>) {
    LazyColumn {
        items(messages) { message ->
            // Display each message
            Text(text = message, style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
fun MessageInput() {
    // TODO Message Input
}