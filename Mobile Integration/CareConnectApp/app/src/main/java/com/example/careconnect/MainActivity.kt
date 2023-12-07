package com.example.careconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.careconnect.ui.theme.CareConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CareConnectTheme {
                // Navigation controller
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Navigation Host
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }
                        composable("dashboard") { DashboardScreen(navController) }
                        composable("patientsList") { PatientsListScreen(navController) }
                        composable("patientDetails/{patientName}") { backStackEntry ->
                            val patientName = backStackEntry.arguments?.getString("patientName")
                            val patient = getPatientByName(patientName ?: "")
                            if (patient != null) {
                                PatientDetailsScreen(patient)
                            } else {
                                // Handle the case where the patient is not found
                            }
                        }
                         }
                }
            }
        }
    }
    fun getPatientByName(name: String): PatientModel? {
        val patients = listOf(
            PatientModel("Patient 1", "Margret Cole", "Diagnosis 1"),
            PatientModel("Patient 2", "Ben Martin", "Diagnosis 2"),
            PatientModel("Patient 3", "Bridget Kenna", "Diagnosis 3"),
        )
        return patients.find { it.name == name }
    }
}