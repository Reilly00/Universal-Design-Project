package com.example.careconnect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Dashboard",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(getDashboardItems()) { dashboardItem ->
                DashboardItem(dashboardItem, navController)
            }
        }

        // Bottom navigation bar at the end of the Column
        BottomNavigationBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardItem(item: DashboardItemModel, navController: NavController?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(90.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            if (item.title == "Patients") {
                navController?.navigate("patientsList")
            }
            // Add additional navigation logic for other items if necessary
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }
    }
}

data class DashboardItemModel(val title: String)

fun getDashboardItems(): List<DashboardItemModel> {
    return listOf(
        DashboardItemModel("Patients"),
        DashboardItemModel("Carer's Portal"),
        DashboardItemModel("Scan Details"),
        DashboardItemModel("Email"),
        DashboardItemModel("View Records"),
        DashboardItemModel("Update Records"),
    )
}