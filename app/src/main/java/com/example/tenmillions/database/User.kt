package com.example.tenmillions.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    val username: String,
    val score: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}