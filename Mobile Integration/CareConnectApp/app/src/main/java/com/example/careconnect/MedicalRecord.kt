package com.example.careconnect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MedicalRecord(record: RecordModel) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Medical Record Details",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
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

        // Bottom navigation bar at the end of the Column
        BottomNavigationBar()
    }
}