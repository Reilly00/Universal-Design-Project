package com.example.careconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.careconnect.ui.theme.CareConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CareConnectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Dashboard()
                }
            }
        }
    }
}

@Composable
fun Dashboard() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Dashboard",
                style = TextStyle.Default,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(getDashboardItems()) { dashboardItem ->
            DashboardItem(dashboardItem)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardItem(item: DashboardItemModel) {
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
                text = item.title,
                style = TextStyle.Default,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = item.subtitle,
                style = TextStyle.Default, // Use bodyMain instead of body1
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

data class DashboardItemModel(val title: String, val subtitle: String)

fun getDashboardItems(): List<DashboardItemModel> {
    return listOf(
        DashboardItemModel("Item 1", "Subtitle for Item 1"),
        DashboardItemModel("Item 2", "Subtitle for Item 2"),
        DashboardItemModel("Item 3", "Subtitle for Item 3"),
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    CareConnectTheme {
        Dashboard()
    }
}
