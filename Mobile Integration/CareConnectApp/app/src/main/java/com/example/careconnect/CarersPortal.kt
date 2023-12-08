package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class MessageModel(val sender: String, val content: String)

@Composable
fun CarersPortal(navController: NavController? = null, loggedInUser: User) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MessagingContent(loggedInUser)
        navController?.let { BottomNavigationBar(it) }
    }
}


@Composable
fun MessagingContent(loggedInUser: User) {
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
        MessageInput(loggedInUser = loggedInUser, onSendMessage = { sender, newMessage ->
            messages = messages + MessageModel(sender.name, newMessage)
        })
    }
}

@Composable
fun MessageList(messages: List<MessageModel>) {
    LazyColumn {
        items(messages) { message ->
            // Display each message
            Text(
                text = "${message.sender}: ${message.content}",
                style = MaterialTheme.typography.titleSmall
            )
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