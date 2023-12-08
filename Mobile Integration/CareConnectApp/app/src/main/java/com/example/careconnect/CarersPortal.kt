package com.example.careconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.careconnect.ui.theme.CareConnectTheme
import androidx.compose.material.icons.filled.Send

data class MessageModel(val sender: String, val content: String)

@Composable
fun CarersPortal(navController: NavController? = null) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MessagingContent()
        navController?.let { BottomNavigationBar(it) }
    }
}

@Composable
fun MessagingContent() {
    var message by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<MessageModel>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display messages
        MessageList(messages = messages)

        // Input for typing messages
        MessageInput(onSendMessage = { sender, newMessage ->
            messages = messages + MessageModel(sender, newMessage)
        })
    }
}
@Composable
fun MessageList(messages: List<MessageModel>) {
    LazyColumn {
        items(messages) { message ->
            // Display each message in a designed layout
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                // Message content in a light pink box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = Color(0xFFF1E0E6), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                ) {
                    Text(
                        text = message.content,
                        color = Color.Black
                    )
                }

                // Sender and timestamp outside the box
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = message.sender,
                        color = Color.Gray,
                    )
                    Text(
                        text = "Timestamp", // Replace with actual timestamp logic
                        color = Color.Gray,
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(onSendMessage: (String, String) -> Unit) {
    var sender by remember { mutableStateOf("John Doe") }
    var message by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),

            placeholder = { Text("Type a message...") },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            colors = TextFieldDefaults.textFieldColors(
                //backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (message.isNotBlank()) {
                        onSendMessage(sender, message)
                        message = ""
                        keyboardController?.hide()
                    }
                }
            )
        )

        IconButton(
            onClick = {
                if (message.isNotBlank()) {
                    onSendMessage(sender, message)
                    message = ""
                    keyboardController?.hide()
                }
            },
            modifier = Modifier.background(Color.Transparent)
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
        }
    }
}