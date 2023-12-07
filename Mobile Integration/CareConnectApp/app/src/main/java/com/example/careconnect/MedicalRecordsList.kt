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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MedicalRecordsList(navController: NavController) {
    // Sample record data
    val records = listOf(
        // Patient Data to be passed through
        RecordModel(1,"Record 1", "2023-01-01"),
        RecordModel(2, "Record 2", "2023-02-15"),
        RecordModel(3, "Record 3", "2023-03-20"),
    )

    // records list screen content
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Records List", style = MaterialTheme.typography.titleLarge, color = Color.Black, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn {
            items(records) { patient ->
                RecordListItem(patient, navController) // Pass NavController here
            }
        }

        BottomNavigationBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordListItem(record: RecordModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            navController.navigate("MedicalRecord/${record.title}") // Navigate to MedicalRecord Screen
        }
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(record.title, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(bottom = 8.dp))
            Text(record.date, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}

// Data model for the records
data class RecordModel(val id: Int, val title: String, val date: String)