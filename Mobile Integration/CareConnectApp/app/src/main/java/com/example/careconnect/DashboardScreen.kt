package com.example.careconnect

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
            text = "Dashboard",
            modifier = Modifier.padding(bottom = 140.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardItem(item: DashboardItemModel, navController: NavController?) {
    Card(
        modifier = Modifier
            .width(152.dp)
            .height(152.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            if (item.title == "Patients") {
                navController?.navigate("patientsList")
            }
            // Add additional navigation logic for other items if necessary
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (item.title == "Patients") {
                        navController?.navigate("patientsList")
                    }
                    // Add additional navigation logic for other items if necessary
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally) 
            )
        }
    }
}

data class DashboardItemModel(val title: String)

fun getDashboardItems(): List<DashboardItemModel> {
    return listOf(
        DashboardItemModel("      Patients"),
        DashboardItemModel(" Carer's Portal"),
        DashboardItemModel("   Scan Details"),
        DashboardItemModel("        Email"),
        DashboardItemModel("  View Records"),
        DashboardItemModel(" Update Record"),
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
