package com.example.careconnect

import android.widget.GridLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.careconnect.ui.theme.CareConnectTheme


@Composable
fun DashboardScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Dashboard",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 35.dp),
            style = MaterialTheme.typography.titleLarge
                .copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            textAlign = TextAlign.Center
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(getDashboardItems().chunked(2)) { rowItems ->
                TwoItemRow(rowItems, navController)
            }
        }


        navController?.let { BottomNavigationBar(it) }
    }
}

@Composable
fun TwoItemRow(items: List<DashboardItemModel>, navController: NavController?) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items) { dashboardItem ->
            DashboardItem(dashboardItem, navController)
        }
    }
}

@Composable
fun DashboardItem(item: DashboardItemModel, navController: NavController?) {
    Card(
        modifier = Modifier
            .width(152.dp)
            .height(152.dp)
            .clickable {
                if (item.title == "Patients") {
                    navController?.navigate("patientsList")
                }

                if (item.title == "Carer's Portal") {
                    navController?.navigate("carersPortal")
                }

                if (item.title == "Scan Details") {
                    navController?.navigate("scanDetails")
                }

                if (item.title == "Email") {
                    navController?.navigate("emailScreen")
                }

                if (item.title == "View Records") {
                    navController?.navigate("viewRecords")
                }
            },
        shape = MaterialTheme.shapes.medium,

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFBB99A5))
                .clickable {
                    if (item.title == "Patients") {
                        navController?.navigate("patientsList")
                    }

                    if (item.title == "Carer's Portal") {
                        navController?.navigate("carersPortal")
                    }

                    if (item.title == "Scan Details") {
                        navController?.navigate("scanDetails")
                    }

                    if (item.title == "Email") {
                        navController?.navigate("emailScreen")
                    }

                    if (item.title == "View Records") {
                        navController?.navigate("viewRecords")
                    }
                },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = item.imageResourceId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.title,
                    color = Color(0xFF00008B),
                   // color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ), modifier = Modifier
                        .wrapContentSize()
                )
            }
        }
    }
}


data class DashboardItemModel(val title: String, val imageResourceId: Int)

fun getDashboardItems(): List<DashboardItemModel> {
    return listOf(
        DashboardItemModel("Patients", R.drawable.patient),
        DashboardItemModel("Carer's Portal", R.drawable.careportal),
        DashboardItemModel("Scan Details", R.drawable.qrcode),
        DashboardItemModel("Email", R.drawable.email),
        DashboardItemModel("View Records", R.drawable.record),
        DashboardItemModel("Update Records", R.drawable.updated)// Update Record Button Moved into the record page itself
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewDashboard() {
    CareConnectTheme {
        DashboardScreen()
    }
}
