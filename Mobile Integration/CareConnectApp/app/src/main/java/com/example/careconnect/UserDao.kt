package com.example.careconnect

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_details WHERE id = :userId")
    suspend fun getUserDetails(userId: Int): UserEntity
}