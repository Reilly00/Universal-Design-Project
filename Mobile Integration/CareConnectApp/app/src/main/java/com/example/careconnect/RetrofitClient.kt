package com.example.careconnect

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

// Data class to match the login response structure
data class LoginResponse(
    val message: String,
    val profile_pic_url: String?, // Nullable to handle cases where it might not be provided
    val userId: Int
)

// Data class for sending login data
data class LoginData(val username: String, val password: String)

// Data class for registration data
data class RegistrationData(val username: String, val email: String, val password: String)

object RetrofitClient {
    private const val BASE_URL = "http://carersconnect.net:5000"

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .retryOnConnectionFailure(true)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()


    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)  // Use the OkHttpClient instance here
            .build()
            .create(ApiService::class.java)
    }
}

interface ApiService {

    @POST("/register")
    suspend fun registerUser(@Body registrationData: RegistrationData): Response<Unit>

    @POST("/login")
    suspend fun loginUser(@Body loginData: LoginData): Response<LoginResponse>

    @Headers("Connection: close")
    @GET("/user/{user_id}/patient_cards")
    suspend fun getServerPatientCardsForUser(@Path("user_id") userId: Int): Response<List<ServerPatientModel>>
}