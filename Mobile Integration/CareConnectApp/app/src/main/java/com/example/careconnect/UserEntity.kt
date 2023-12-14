package com.example.careconnect

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val location: String,
    val dateOfBirth: String,
    val bio: String
)