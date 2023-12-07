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
fun PatientsListScreen(navController: NavController) {
    // Sample patient data
    val patients = listOf(
        // Patient Data to be passed through
        PatientModel("Patient 1", "Margret Cole", "Diagnosis 1"),
        PatientModel("Patient 2", "Ben Martin", "Diagnosis 2"),
        PatientModel("Patient 3", "Bridget Kenna", "Diagnosis 3"),
    )

    // patients list screen content
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Patients List", style = MaterialTheme.typography.titleLarge, color = Color.Black, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn {
            items(patients) { patient ->
                PatientListItem(patient, navController) // Pass NavController here
            }
        }

        navController?.let { BottomNavigationBar(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListItem(patient: PatientModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            navController.navigate("patientDetails/${patient.name}") // Navigate to PatientDetailsScreen
        }
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(patient.number, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(bottom = 8.dp))
            Text(patient.name, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}

// Data model for the patients
data class PatientModel(val number: String, val name: String, val diagnosis: String)