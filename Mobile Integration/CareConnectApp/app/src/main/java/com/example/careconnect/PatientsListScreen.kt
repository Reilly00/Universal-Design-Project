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

@Composable
fun PatientsListScreen() {
    // Sample patient data
    val patients = listOf(
        PatientModel("Patient 1", "Margret Cole"),
        PatientModel("Patient 2", "Ben Martin"),
        PatientModel("Patient 3", "Bridget Kenna"),
    )

    // patients list screen content
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Patients List",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(patients) { patient ->
                PatientListItem(patient)
            }
        }

        // Bottom navigation bar at the end of the Column
        BottomNavigationBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListItem(patient: PatientModel) {
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
                text = patient.number,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            Text(
                text = patient.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

data class PatientModel(val number: String, val name: String)