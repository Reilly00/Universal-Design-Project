import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.navigation.NavController
import com.example.careconnect.R

@Composable
fun ProfileScreen(navController: NavController? = null, profilePicUrl: String? = null) {
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

        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 30.dp)
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
        }

        ProfileContent(profilePicUrl ?: "")
    }
}

@Composable
fun ProfileContent(profilePicUrl: String) {
    val userDetails = listOf(
        UserDetailModel("Name", "Mary Mc Donald"),
        UserDetailModel("Email", "mcdonald.mary@gmail.com"),
        UserDetailModel("Phone", "+353 085 123 4567"),
        UserDetailModel("Location", "Dublin, Ireland"),
        UserDetailModel("Date of Birth", "January 15, 1990"),
        UserDetailModel("Bio", "Passionate about technology and healthcare."),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        ProfileHeader(profilePicUrl)

        LazyColumn {
            items(userDetails) { detail ->
                UserDetailItem(detail)
            }
        }
    }
}

@Composable
fun ProfileHeader(imageUrl: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imagePainter = rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(true)
            }
        )

        Image(
            painter = imagePainter,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "User Profile",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF00008B),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailItem(detail: UserDetailModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = { /* Handle item click here */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFBB99A5), shape = MaterialTheme.shapes.medium)
                .padding(2.dp)
        ) {
            Text(
                text = detail.title,
                color = Color(0xFF00008B),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(start = 16.dp),
            )

            Text(
                text = detail.subtitle,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(start = 16.dp)
            )
        }
    }
}

data class UserDetailModel(val title: String, val subtitle: String)