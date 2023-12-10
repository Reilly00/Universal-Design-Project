package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
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
fun MedicalRecord(record: RecordModel, navController: NavController) {
    // Medical Record screen content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F1F2), Color(0xFF947B83)),
                    startY = -1.5f,
                    endY = 2800f
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Medical Record Details",
            style = MaterialTheme.typography.titleLarge
                .copy(fontWeight = FontWeight.Bold, fontSize = 22.sp),
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 65.dp)
        )

        Text(
            text = "Title: ${record.title}",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Date: ${record.date}",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // "Update Record" button
        Button(
            onClick = {
                // Navigate to UpdateMedicalRecordActivity with the current record details
                navController.navigate("UpdateMedicalRecord/${record.id}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 16.dp)
        ) {
            Text("Update Record")
        }

        // Bottom navigation bar at the end of the Column
        navController?.let { BottomNavigationBar(it) }
    }
}
