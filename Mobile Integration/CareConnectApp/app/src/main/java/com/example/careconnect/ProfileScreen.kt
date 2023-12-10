import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.careconnect.BottomNavigationBar
import com.example.careconnect.ui.theme.CareConnectTheme
import com.example.careconnect.R


@Composable
fun ProfileScreen(navController: NavController? = null) {
    val lightPinkColor = Color(0xFFF5F1F2)
    val strongerPinkColor = Color(0xFF947B83)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(lightPinkColor, strongerPinkColor),
                    startY = -1.5f,
                    endY = 2800f
                )
            )
    ) {

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
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
        }

        // Content
        ProfileContent()
    }
}

data class UserDetailModel(val title: String, val subtitle: String)
@Composable
fun ProfileContent() {
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
        // Header with profile picture
        ProfileHeader()

        // User details
        LazyColumn {
            items(userDetails) { detail ->
                UserDetailItem(detail)
            }
        }
    }
}

@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "User Profile",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
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
               // textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(start = 16.dp),
            )

            Text(
                text = detail.subtitle,
                style = MaterialTheme.typography.titleLarge,
                //textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(start = 16.dp)
            )
        }
    }
}
