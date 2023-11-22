package com.example.careconnect

fun isEmailValid(email: String): Boolean {
    return email.contains("@") && email.substringAfter("@").contains(".")
}

fun isPasswordValid(password: String): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    return password.matches(passwordPattern.toRegex())
}

fun getPasswordStrength(password: String): PasswordStrength {
    return when {
        password.length >= 10 && password.any { it.isDigit() } && password.any { it.isUpperCase() } -> PasswordStrength.STRONG
        password.length >= 8 -> PasswordStrength.MEDIUM
        password.isNotEmpty() -> PasswordStrength.WEAK
        else -> PasswordStrength.NONE
    }
}

enum class PasswordStrength {
    NONE, WEAK, MEDIUM, STRONG
}