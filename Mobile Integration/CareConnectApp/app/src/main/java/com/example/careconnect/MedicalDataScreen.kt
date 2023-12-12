//package com.example.careconnect
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import io.github.franmcod.pubnub_compose.PubNub
//import io.github.franmcod.pubnub_compose.Subscribe
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//
//@OptIn(ExperimentalCoroutinesApi::class)
//@Composable
//fun MedicalDataScreen(navController: NavController) {
//    var medicalData by remember { mutableStateOf("Waiting for information...") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        // Use the PubNub Compose library to subscribe to the channel
//        PubNub(
//            subscribeKey = "sub-c-8c90cb31-1954-45e8-a659-336e8c6d0668", // Replace with your actual subscribe key
//            channels = listOf("medical_data_channel")
//        ) {
//            Subscribe { message ->
//                // Handle medical data updates
//                medicalData = "Medical Data id: ${message["patient_id"]}"
//            }
//        }
//
//        // Display medical data
//        Text(
//            text = medicalData,
//            style = MaterialTheme.typography.h5,
//            modifier = Modifier
//                .padding(8.dp)
//        )
//    }
//}
