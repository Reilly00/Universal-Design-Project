package com.example.careconnect

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitClient {
    private const val BASE_URL = "http://13.51.157.194:5000"

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
}

data class RegistrationData(val username: String, val email: String, val password: String)