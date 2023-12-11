@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class MessageModel(val sender: String, val content: String)


val lightPinkColor = Color(0xFFF5F1F2)
val strongerPinkColor = Color(0xFF947B83)

@Composable
fun CarersPortal(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(lightPinkColor, strongerPinkColor),
                    startY = -1.5f,
                    endY = 2800f
                )
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController?.popBackStack()
                },
                modifier = Modifier
                    .background(
                        color = Color(0xFFBB99A5),
                        shape = CircleShape
                    )
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "Care's Portal",
                color = Color(0xFF00008B),
                style = MaterialTheme.typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        MessagingContent(navController = navController)
    }
}



@Composable
fun MessagingContent(navController: NavController? = null) {
    var messages by remember { mutableStateOf(listOf(
        MessageModel("Doctor Smith", "We assessed the patient today."),
        MessageModel("Nurse Johnson", "Patient's vitals are stable."),
        MessageModel("Caregiver", "Administered medications and recorded patient's activities.")
    )) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display messages
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            itemsIndexed(messages) { index, message ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(color = Color(0xFFE6C5D0), shape = MaterialTheme.shapes.medium)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = message.content,
                            color = Color.Black
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = message.sender,
                            color = Color.DarkGray,
                        )
                    }

                    if (index == messages.size - 1) {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // Message input
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            MessageInput(onSendMessage = { sender, newMessage ->
                // Add the new message at the end of the list
                messages = messages + MessageModel(sender, newMessage)
            })
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
                //.weight(1f)
                .padding(end = 8.dp),

            placeholder = { Text("Type a message...") },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            colors = TextFieldDefaults.textFieldColors(
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
