package com.example.careconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.careconnect.ui.theme.CareConnectTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CareConnectTheme {
                // Set up the navigation host
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "dashboard") {
                    composable("dashboard") { Dashboard(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("settings") { SettingsScreen(navController) }
                    composable("notifications") { NotificationsScreen(navController) }
                    composable("profile") { ProfileScreen(navController) }

                    // Bottom navigation bar
                    BottomNavigationBar(navController)
                }
            }
        }
    }
}

// Create a separate composable for each screen
@Composable
fun Dashboard(navController: NavController) {
    // Your dashboard content goes here
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Dashboard",
                style = TextStyle.Default,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(getDashboardItems()) { dashboardItem ->
            DashboardItem(dashboardItem)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    // Your home screen content goes here
}

@Composable
fun SettingsScreen(navController: NavController) {
    // Your settings screen content goes here
}

@Composable
fun NotificationsScreen(navController: NavController) {
    // Your notifications screen content goes here
}

@Composable
fun ProfileScreen(navController: NavController) {
    // Your profile screen content goes here
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardItem(item: DashboardItemModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = { /* Handle item click here */ }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = item.title,
                style = TextStyle.Default,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = item.subtitle,
                style = TextStyle.Default,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

data class DashboardItemModel(val title: String, val subtitle: String)

fun getDashboardItems(): List<DashboardItemModel> {
    return listOf(
        DashboardItemModel("Item 1", "Subtitle for Item 1"),
        DashboardItemModel("Item 2", "Subtitle for Item 2"),
        DashboardItemModel("Item 3", "Subtitle for Item 3"),
    )
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Settings", Icons.Default.Settings, "settings"),
        BottomNavItem("Notifications", Icons.Default.Notifications, "notifications"),
        BottomNavItem("Profile", Icons.Default.Person, "profile"),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        navItems.forEach { navItem ->
            IconButton(
                onClick = {
                    navController.navigate(navItem.route)
                }
            ) {
                Icon(
                    imageVector = navItem.icon,
                    contentDescription = null
                )
            }
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    // You can preview the BottomNavigationBar here
}