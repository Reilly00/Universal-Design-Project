package com.example.careconnect

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Data class to match the login response structure
data class LoginResponse(
    val message: String,
    val profile_pic_url: String? // Nullable to handle cases where it might not be provided
)

// Data class for sending login data
data class LoginData(val username: String, val password: String)

// Data class for registration data
data class RegistrationData(val username: String, val email: String, val password: String)

object RetrofitClient {
    private const val BASE_URL = "http://carersconnect.net:5000"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

interface ApiService {
    @POST("/register")
    suspend fun registerUser(@Body registrationData: RegistrationData): Response<Unit>

    @POST("/login")
    suspend fun loginUser(@Body loginData: LoginData): Response<LoginResponse>
}