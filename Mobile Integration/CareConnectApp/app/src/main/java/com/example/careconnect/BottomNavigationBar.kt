package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Settings", Icons.Default.Settings),
        BottomNavItem("Notifications", Icons.Default.Notifications),
        BottomNavItem("Profile", Icons.Default.Person),
    )

    val customBackgroundColor = Color(0xFFBB99A5).copy(alpha = 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .background(color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val navBackStackEntry by rememberUpdatedState(LocalContext.current)

            navItems.forEachIndexed { index, navItem ->
                IconButton(
                    onClick = {

                        // Handle navigation to the corresponding screen
                        if (index == 0) {
                            navController.navigate("dashboard")
                        }

                        if (index == 1) {
                            navController.navigate("settings")
                        }

                        if (index == 2) {
                            navController.navigate("notifications")
                        }

                        if (index == 3) {
                            navController.navigate("profile")
                        }
                    },
                    modifier = Modifier
                        .background(
                            //color = Color.Magenta,
                            color= customBackgroundColor,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector)
