package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
@Composable
fun NotificationsScreen(navController: NavController, userViewModel: UserViewModel) {
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
            )
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Notifications",
            color = Color(0xFF00008B),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 22.dp),
            style = MaterialTheme.typography.titleLarge
                .copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            textAlign = TextAlign.Center
        )

        // Content
        NotificationsContent()
        navController?.let { BottomNavigationBar(it, userViewModel) }
    }
}


@Composable
fun NotificationsContent() {
    LazyColumn {
        items(getNotificationItems()) { notificationItem ->
            NotificationItem(notificationItem)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationItem(item: NotificationItemModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = { /* Handle item click here */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFBB99A5), shape = MaterialTheme.shapes.medium)
                .padding(2.dp)
        ) {
            Text(
                text = item.title,
               // color = Color.White,
                color = Color(0xFF00008B),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(start = 16.dp),
            )

            Text(
                text = item.content,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(start = 16.dp)
            )
        }
    }
}

data class NotificationItemModel(val title: String, val content: String)

fun getNotificationItems(): List<NotificationItemModel> {
    return listOf(
        NotificationItemModel("Appointment Reminder", "Don't forget your appointment."),
        NotificationItemModel("Task Completed", "Task assigned to you has been completed."),
        NotificationItemModel("Appointment Confirmation", "Your appointment is confirmed."),
        NotificationItemModel("Appointment Update", "Update on your upcoming appointment."),
        )
}
