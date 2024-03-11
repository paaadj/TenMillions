package com.example.tenmillions.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @ColumnInfo(name = "question_text")
    val questionText: String,
    @ColumnInfo(name = "correct_answer")
    val correctAnswer: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    @ColumnInfo(defaultValue = "ru")
    val lang: String = "ru",
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}