package com.example.careconnect

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PatientsListScreen(navController: NavController? = null, qrCodeContent: String? = null, userViewModel: UserViewModel) {
    val lightPinkColor = Color(0xFFF5F1F2)
    val strongerPinkColor = Color(0xFF947B83)

    if (qrCodeContent == null) {
        val userId by userViewModel.userId.collectAsState()
        LaunchedEffect(key1 = userId) {
            userId?.let { id ->
                userViewModel.fetchServerPatientCards(id)
            }
        }
    }

    val serverPatientCards by userViewModel.serverPatientCards.collectAsState()

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
        Header(navController)
        PatientCardsList(navController, serverPatientCards)
    }
}

@Composable
fun Header(navController: NavController?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Color.Transparent,
                shape = CircleShape
            )
    ) {
        IconButton(
            onClick = {
                navController?.popBackStack()
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
            text = "Patients",
            style = MaterialTheme.typography.titleLarge
                .copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            color = Color(0xFF00008B),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 130.dp)
        )
    }
}

@Composable
fun PatientCardsList(navController: NavController?, patientCards: List<ServerPatientModel>) {
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn {
        items(patientCards) { patientCard ->
            ServerPatientListItem(patientCard, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerPatientListItem(patientCard: ServerPatientModel, navController: NavController?) {
    val starAlpha = remember { Animatable(0f) }

    LaunchedEffect(starAlpha) {
        starAlpha.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 500),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            // Implement navigation or action on card click
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFBB99A5), shape = MaterialTheme.shapes.medium),
        ) {
            Image(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .offset(280.dp, 20.dp)
                    .alpha(starAlpha.value)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = patientCard.patient_id ?: "Unknown ID",
                    color = Color(0xFF00008B),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
                Text(
                    text = patientCard.medical_data ?: "No Data",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
        }
    }
}


fun createPatientModelFromQRCode(qrCodeContent: String): PatientModel {
    val id = qrCodeContent
    val patientId = "Patient $id"
    val name = "Patient Name $id"
    val diagnosis = "Diagnosis $id"

    return PatientModel( patientId, name, diagnosis)
}

// Data model for the patients
data class PatientModel( val patientId: String, val name: String, val diagnosis: String)