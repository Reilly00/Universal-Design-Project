package com.example.careconnect

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun BottomNavigationBar(navController: NavController, userViewModel: UserViewModel) {
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
            var rotationState by remember { mutableStateOf(0f) }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(16) // Delay to control rotation speed
                    rotationState += 1f
                }
            }

            navItems.forEachIndexed { index, navItem ->
                IconButton(
                    onClick = {
                        when (index) {
                            0 -> navController.navigate("dashboard")
                            1 -> navController.navigate("settings")
                            2 -> navController.navigate("notifications")
                            3 -> {
                                val profilePicUrl = userViewModel.profilePicUrl.value
                                if (!profilePicUrl.isNullOrEmpty()) {
                                    val encodedProfilePicUrl = Uri.encode(profilePicUrl)
                                    navController.navigate("profile/$encodedProfilePicUrl")
                                } else {
                                    // Handle the case where profilePicUrl is not available
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .background(
                            color = customBackgroundColor,
                            shape = CircleShape
                        )
                ) {
                    if (index == 1) {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(36.dp)
                                .rotate(rotationState)
                        )
                    } else {
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
}

data class BottomNavItem(val label: String, val icon: ImageVector)