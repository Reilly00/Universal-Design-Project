package com.example.careconnect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.careconnect.ui.theme.CareConnectTheme

@Composable
fun NotificationsScreen() {
    // TODO Implement the UI for the notifications screen
}

data class NotificationItemModel(val title: String, val content: String)

fun getNotificationItems(): List<NotificationItemModel> {
    return listOf(
        NotificationItemModel("New Message", "You have a new message."),
        NotificationItemModel("Appointment Reminder", "Don't forget your appointment."),
        NotificationItemModel("Task Completed", "Task assigned to you has been completed."),
    )
}

@Preview(showBackground = true)
@Composable
fun NotificationsContentPreview() {

}