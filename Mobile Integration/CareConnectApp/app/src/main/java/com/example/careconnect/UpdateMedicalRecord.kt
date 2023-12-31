package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun UpdateMedicalRecord(record: RecordModel, navController: NavController? = null, userViewModel: UserViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                // Back button
                IconButton(
                    onClick = {
                        // Navigate back
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }

            // TODO Title input

            // TODO Date input

            // "Update Record" button
            Button(
                onClick = {
                    // Handle the update logic
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 16.dp)
            ) {
                Text("Update Record")
            }

            // Bottom navigation bar at the end of the Column
            navController?.let { BottomNavigationBar(it, userViewModel) }
        }
    }
}
