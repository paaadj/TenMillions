package com.example.tenmillions.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions")
    fun getAll(): List<Question>

    @Query("SELECT * FROM questions WHERE uid = :id")
    fun getById(id: Int): Question

    @Insert
    fun insert(question: Question)

    @Update
    fun update(question: Question)

    @Delete
    fun delete(question: Question)
}