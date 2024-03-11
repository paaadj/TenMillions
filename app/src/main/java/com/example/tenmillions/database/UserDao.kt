package com.example.tenmillions.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY score DESC LIMIT 10")
    suspend fun getAll(): MutableList<User>

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT EXISTS (SELECT * FROM users WHERE username = :username)")
    suspend fun checkUsername(username: String): Boolean
}