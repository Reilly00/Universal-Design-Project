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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            text = "Care Connect",
            modifier = Modifier.padding(bottom = 60.dp),
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            items(getDashboardItems().chunked(2)) { rowItems ->
                TwoItemRow(rowItems, navController)
            }
        }

        // Bottom navigation bar at the end of the Column
        BottomNavigationBar()
    }
}


@Composable
fun TwoItemRow(items: List<DashboardItemModel>, navController: NavController?) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(27.dp),
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
                // Add additional navigation logic for other items if necessary
            },
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (item.title == "Patients") {
                        navController?.navigate("patientsList")
                    }
                    // Add additional navigation logic for other items if necessary
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.title,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ), modifier = Modifier
                            .wrapContentSize()
                    )
                }
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
        DashboardItemModel("Update Record", R.drawable.updated),
    )
}


//@Composable
//fun BottomNavigationBar() {
//    // Implement your Bottom Navigation Bar here
//}

@Preview(showBackground = true)
@Composable
fun PreviewDashboard() {
    CareConnectTheme {
        DashboardScreen()
    }
}
