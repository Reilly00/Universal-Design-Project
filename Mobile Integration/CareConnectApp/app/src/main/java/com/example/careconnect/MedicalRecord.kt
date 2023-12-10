package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
            color = Color(0xFF00008B),
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


        Button(
            onClick = {
                navController.navigate("UpdateMedicalRecord/${record.id}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 16.dp)
        ) {
            Text("Update Record")
        }


        navController?.let { BottomNavigationBar(it) }
    }
}

