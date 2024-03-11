package com.example.tenmillions.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions")
    suspend fun getAll(): List<Question>

    @Query("SELECT * FROM questions WHERE uid = :id")
    suspend fun getById(id: Int): Question?

    @Query("SELECT * FROM questions WHERE lang=:lang ORDER BY RANDOM() LIMIT 10")
    suspend fun getRandomItems(lang: String): MutableList<Question>

    @Insert
    suspend fun insert(question: Question)

    @Update
    suspend fun update(question: Question)

    @Delete
    suspend fun delete(question: Question)

    @Query("DELETE FROM questions")
    suspend fun clear()
}