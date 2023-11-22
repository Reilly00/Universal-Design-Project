package com.example.careconnect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Dashboard",
            style = TextStyle.Default,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(getDashboardItems()) { dashboardItem ->
                DashboardItem(dashboardItem)
            }
        }

        // Bottom navigation bar at the end of the Column
        BottomNavigationBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardItem(item: DashboardItemModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(90.dp),
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