package com.example.careconnect

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.ProtocolException

class UserViewModel : ViewModel() {
    private val _profilePicUrl = MutableStateFlow<String?>(null)
    val profilePicUrl: StateFlow<String?> = _profilePicUrl

    private val _serverPatientCards = MutableStateFlow<List<ServerPatientModel>>(listOf())
    val serverPatientCards: StateFlow<List<ServerPatientModel>> = _serverPatientCards

    // New state for storing user ID
    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> = _userId

    fun updateProfilePicUrl(url: String?) {
        viewModelScope.launch {
            _profilePicUrl.value = url
        }
    }

    // New function to update user ID
    fun updateUserId(id: Int?) {
        viewModelScope.launch {
            _userId.value = id
        }
    }

    fun fetchServerPatientCards(userId: Int, retryCount: Int = 10) {
        viewModelScope.launch {
            var attempts = 0
            while (attempts < retryCount) {
                try {
                    val response = RetrofitClient.instance.getServerPatientCardsForUser(userId)
                    if (response.isSuccessful && response.body() != null) {
                        _serverPatientCards.value = response.body()!!
                        Log.d(
                            "UserViewModel",
                            "Fetched server patient cards: ${_serverPatientCards.value}"
                        )
                        break
                    } else {
                        Log.d("UserViewModel", "Response not successful or body is null")
                        _serverPatientCards.value = listOf() // or a specific error state
                        attempts++
                    }
                } catch (e: Exception) {
                    if (e is ProtocolException && e.message?.contains(
                            "unexpected end of stream",
                            ignoreCase = true
                        ) == true
                    ) {
                        Log.e("UserViewModel", "Retrying... Attempt ${attempts + 1}")
                        attempts++
                    } else {
                        Log.e(
                            "UserViewModel",
                            "Error fetching server patient cards: ${e.message}",
                            e
                        )
                        break
                    }
                }
            }
        }
    }
}

    // Data model for server patient cards
data class ServerPatientModel(
    val id: Int,
    val patient_id: String,
    val medical_data: String
)