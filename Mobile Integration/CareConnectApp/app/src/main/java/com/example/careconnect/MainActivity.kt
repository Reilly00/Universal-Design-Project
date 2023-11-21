package com.example.careconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.careconnect.ui.theme.CareConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CareConnectTheme {
                // Navigation controller
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Navigation Host
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController? = null) {
  
fun Dashboard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Dashboard",
            style = TextStyle.Default,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(getDashboardItems()) { dashboardItem ->
                DashboardItem(dashboardItem)
            }
        }

        // Bottom navigation bar at the end of the Column
        BottomNavigationBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardItem(item: DashboardItemModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(90.dp),
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
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }
    }
}

data class DashboardItemModel(val title: String)

fun getDashboardItems(): List<DashboardItemModel> {
    return listOf(
        DashboardItemModel("Patients"),
        DashboardItemModel("Carer's Portal"),
        DashboardItemModel("Scan Details"),
        DashboardItemModel("Email"),
        DashboardItemModel("View Records"),
        DashboardItemModel("Update Records"),
    )
}
    
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Care Connect",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(128.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it.trim() },
            label = { Text("Email") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it.trim() },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* TODO: Add login logic here, not needed for current build */ },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Don't have an account? Register here",
            modifier = Modifier.clickable { navController?.navigate("register") }
        )
        Spacer(modifier = Modifier.weight(1f)) // Flexible spacer to balance the content
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController? = null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var passwordRequirementError by remember { mutableStateOf("") }

    // Additional state for password strength
    val passwordStrength = getPasswordStrength(password)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Care Connect",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(128.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() },
            label = { Text("Email") },
            singleLine = true,
            isError = emailError.isNotEmpty()
        )

        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it.trim()
            },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError || passwordRequirementError.isNotEmpty()
        )

        // Display Password Strength Meter
        PasswordStrengthMeter(strength = passwordStrength)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it.trim() },
            label = { Text("Confirm Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError
        )

        if (passwordError) {
            Text(
                text = "Passwords do not match",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        if (passwordRequirementError.isNotEmpty()) {
            Text(
                text = passwordRequirementError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                emailError = if (!isEmailValid(email)) "Invalid email format" else ""
                passwordError = password != confirmPassword
                if (passwordError) {
                    passwordRequirementError = ""
                } else if (!isPasswordValid(password)) {
                    passwordRequirementError = "Password must:\n Be at least 8 characters, \n include an uppercase letter, \n a lowercase letter, symbol, and a number."
                } else {
                    passwordRequirementError = ""
                    // TODO: Add registration logic here if passwords match and meet requirements
                }
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Already have an account? Log-in",
            modifier = Modifier.clickable { navController?.navigate("login") }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

// Function to check email format
fun isEmailValid(email: String): Boolean {
    return email.contains("@") && email.substringAfter("@").contains(".")
}

// Function to check password requirements
fun isPasswordValid(password: String): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    return password.matches(passwordPattern.toRegex())
}

// Function to check password strength
private fun getPasswordStrength(password: String): PasswordStrength {
    return when {
        password.length >= 10 && password.any { it.isDigit() } && password.any { it.isUpperCase() } -> PasswordStrength.STRONG
        password.length >= 8 -> PasswordStrength.MEDIUM
        password.isNotEmpty() -> PasswordStrength.WEAK
        else -> PasswordStrength.NONE
    }
}

// Enum to represent password strength levels
enum class PasswordStrength {
    NONE, WEAK, MEDIUM, STRONG
}

// Composable to display password strength meter
@Composable
fun PasswordStrengthMeter(strength: PasswordStrength) {
    val strengthColor = when (strength) {
        PasswordStrength.STRONG -> Color.Green // Strong password
        PasswordStrength.MEDIUM -> Color(0xFFFFA500) // Medium strength - Orange color
        PasswordStrength.WEAK -> Color.Red // Weak password
        PasswordStrength.NONE -> Color.Gray // No password entered
    }

    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Password Strength: ", style = MaterialTheme.typography.bodySmall)
        Box(modifier = Modifier
            .width(100.dp)
            .height(10.dp)
            .background(strengthColor))
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    CareConnectTheme {
        Dashboard()
    }
}

@Composable
fun BottomNavigationBar() {
    val navItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Settings", Icons.Default.Settings),
        BottomNavItem("Notifications", Icons.Default.Notifications),
        BottomNavItem("Profile", Icons.Default.Person),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.background(color = Color.Blue)
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val navBackStackEntry by rememberUpdatedState(LocalContext.current)

            navItems.forEach { navItem ->
                IconButton(
                    onClick = {
                        // Handle navigation to the corresponding screen
                    }
                ) {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
fun DefaultPreview() {
    CareConnectTheme {
        // Static preview of the LoginScreen without navigation functionality
        LoginScreen()
    }
}