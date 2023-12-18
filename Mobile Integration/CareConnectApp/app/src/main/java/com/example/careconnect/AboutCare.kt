package com.example.careconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AboutCare(navController: NavController) {
    val lightPinkColor = Color(0xFFF5F1F2)
    val strongerPinkColor = Color(0xFF947B83)

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
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "About Care",
            color = Color(0xFF00008B),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            style = MaterialTheme.typography.titleLarge
                .copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            textAlign = TextAlign.Center
        )

        // Content
        CareContent()

    }
}


@Composable
fun CareContent() {
    LazyColumn {
        items(getCareContent()) { careItem ->
            careItem(careItem)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun careItem(item: CareItemModel) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFBB99A5), shape = MaterialTheme.shapes.medium)
                .padding(2.dp)
        ) {
            Text(
                text = item.title,
                color = Color(0xFF00008B),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(start = 16.dp),
            )

            val contentText = if (expanded) {
                item.content
            } else {
                item.content.take(150) + "..."
            }

            SelectionContainer {
                Text(
                    text = AnnotatedString(contentText),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(start = 10.dp)
                        .clickable { expanded = !expanded }
                )
            }

            if (!expanded) {
                Text(
                    text = "Read More",
                    color = Color.Blue,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { expanded = true }
                )
            }
        }
    }
}
data class CareItemModel(val title: String, val content: String)

fun getCareContent(): List<CareItemModel> {
    return listOf(
        CareItemModel("Statement ", "Care Connect is an intuitive digital platform designed to revolutionize dementia care. Our mission is to empower caregivers, be it family members or professionals, by simplifying the data sharing process. With Care Connect, onboarding new carers becomes seamless, ensuring continuity and personalized care for those living with dementia. Our mobile application offers not just routine management tools but day-to-day planning, ensuring that nothing is ever forgotten. Experience the ease of day-to-day management with Care Connect – where caring for your loved ones is made simpler and more effective."),
        CareItemModel("Usability", "Focused on providing a product usable for everyone, anywhere. Our user interface is designed with simplicity and accessibility in mind, making it easy for both caregivers and those in their care to use the platform."),
        CareItemModel("Streamlined Data Sharing", "Alleviating the burden on family members and caregivers by making the process of onboarding professionals significantly easier. Our streamlined data sharing ensures that relevant information is accessible to all authorized individuals, facilitating collaboration and improving the overall care experience."),
    )
}