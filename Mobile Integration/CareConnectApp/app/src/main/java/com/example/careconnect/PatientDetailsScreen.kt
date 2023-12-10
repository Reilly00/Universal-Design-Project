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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PatientDetailsScreen(patient: PatientModel, navController: NavController? = null) {
    val lightPinkColor = Color(0xFFF5F1F2)
    val strongerPinkColor = Color(0xFF947B83)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(lightPinkColor, strongerPinkColor),
                    startY = -1.5f,
                    endY = 2800f
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                )
        ) {

            Text(
                text = "Patient Details",
                style = MaterialTheme.typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Patient information
        Text(
            text = "Name: ${patient.name}",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp, start = 20.dp)
        )

        Text(
            text = "Diagnosis: ${patient.diagnosis}",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp, start = 20.dp)
        )

        // Bottom navigation bar at the end of the Column
        navController?.let { BottomNavigationBar(it) }
    }
}

