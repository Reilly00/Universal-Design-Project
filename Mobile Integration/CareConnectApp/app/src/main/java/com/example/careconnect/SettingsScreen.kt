package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment.Companion as Alignment1


@Composable
fun SettingsScreen(navController: NavController? = null) {
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
        Spacer(modifier = Modifier.height(16.dp))

        // Overlay the "Settings" text on top of the Row
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge
                .copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            color = Color(0xFF00008B),
            modifier = Modifier.padding(bottom = 22.dp)
        )

        // Content
        SettingsContent()
        navController?.let { BottomNavigationBar(it) }
    }
}


@Composable
fun SettingsContent() {
    LazyColumn {
        item {
            SettingsItem(
                title = "Account Settings",
                icon = Icons.Default.Person,
                onClick = { /* Handle click for account settings */ }
            )
        }
        item {
            SettingsItem(
                title = "App Settings",
                icon = Icons.Default.Settings,
                onClick = { /* Handle click for app settings */ }
            )
        }
        item {
            SettingsItem(
                title = "Notifications",
                icon = Icons.Default.Notifications,
                onClick = { /* Handle click for notification settings */ }
            )
        }
        item {
            SettingsItem(
                title = "Privacy",
                icon = Icons.Default.Lock,
                onClick = { /* Handle click for privacy settings */ }
            )
        }
        item {
            SettingsItem(
                title = "Help & Support",
                icon = Icons.Default.Build,
                onClick = { /* Handle click for help and support */ }
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 16.dp, end = 16.dp),
       // shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFBB99A5), shape = MaterialTheme.shapes.medium)
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment1.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
