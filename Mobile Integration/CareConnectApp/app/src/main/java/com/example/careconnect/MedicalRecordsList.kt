package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MedicalRecordsList(navController: NavController) {
    // Sample record data
    val records = listOf(
        RecordModel(1, "Record 1", "2023-01-01"),
        RecordModel(2, "Record 2", "2023-02-15"),
        RecordModel(3, "Record 3", "2023-03-20"),
    )

    // Records list screen content
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .background(
                        color = Color(0xFFBB99A5),
                        shape = CircleShape
                    )
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "Medical Records",
                style = MaterialTheme.typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                color = Color(0xFF00008B),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 29.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(records) { record ->
                RecordListItem(record, navController)
            }
        }

        navController?.let { BottomNavigationBar(it) }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordListItem(record: RecordModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            navController.navigate("recordDetails/${record.title}")
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFBB99A5),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp)
        ) {
            Text(
                record.title,
                color = Color(0xFF00008B),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                record.date,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

// Data model for the records
data class RecordModel(val id: Int, val title: String, val date: String)
