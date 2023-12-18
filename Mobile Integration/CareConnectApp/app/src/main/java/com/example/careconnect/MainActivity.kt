package com.example.careconnect

import ProfileScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.careconnect.ui.theme.CareConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CareConnectTheme {
                val navController = rememberNavController()
                val userViewModel: UserViewModel = viewModel()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Navigation Host
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController, userViewModel)

                        }
                        composable("register") {
                            RegisterScreen(navController)
                        }
                        composable("dashboard") {
                            DashboardScreen(navController, userViewModel)
                        }
                        composable("patientsList") {
                            PatientsListScreen(navController, userViewModel = userViewModel)
                        }
                        composable("carersPortal") {
                            CarersPortal(navController)
                        }
                        composable("scanDetails") {
                            ScanDetailsScreen(navController)
                        }
                        composable("emailScreen") {
                            EmailScreen(navController)
                        }
                        composable("viewRecords") {
                            MedicalRecordsList(navController)
                        }
                        composable("settings") {
                            SettingsScreen(navController, userViewModel)
                        }
                        composable("notifications") {
                            NotificationsScreen(navController, userViewModel)
                        }
                        composable("AboutCareConnect") {
                            AboutCare(navController)
                        }
                        composable("profile/{profilePicUrl}") { backStackEntry ->
                            val profilePicUrl = backStackEntry.arguments?.getString("profilePicUrl")
                            ProfileScreen(navController, profilePicUrl)
                        }
                        composable("patientDetails/{patientName}") { backStackEntry ->
                            val patientName = backStackEntry.arguments?.getString("patientName")
                            val patient = getPatientByName(patientName ?: "")
                            if (patient != null) {
                                PatientDetailsScreen(patient)
                            } else {
                                // Handle the case where the patient is not found
                            }
                        }

                        composable("recordDetails/{recordTitle}") { backStackEntry ->
                            val recordTitle = backStackEntry.arguments?.getString("recordTitle")
                            val record = getRecordByTitle(recordTitle ?: "")
                            if (record != null) {
                                MedicalRecord(record, navController, userViewModel)
                            } else {
                                // Handle the case where the record is not found
                            }
                        }
                        composable("UpdateMedicalRecord/{recordId}") { backStackEntry ->
                            val recordTitle = backStackEntry.arguments?.getString("recordTitle")
                            val record = getRecordByTitle(recordTitle ?: "")
                            if (record != null) {
                                UpdateMedicalRecord(record, navController, userViewModel)
                            } else {
                                // Handle the case where the record is not found
                            }
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

fun getRecordByTitle(title: String): RecordModel? {
    val records = listOf(
        RecordModel(1, "Record 1", "2023-01-01"),
        RecordModel(2, "Record 2", "2023-02-15"),
        RecordModel(3, "Record 3", "2023-03-20"),
        // Add more records as needed
    )
    return records.find { it.title == title }
}