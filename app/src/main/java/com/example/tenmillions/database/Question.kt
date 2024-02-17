package com.example.tenmillions.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(

    @PrimaryKey(autoGenerate = true)
    val uid: Int,

    @ColumnInfo(name = "question_text")
    val questionText: String,
    @ColumnInfo(name = "correct_answer")
    val correctAnswer: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
)