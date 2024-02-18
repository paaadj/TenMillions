package com.example.tenmillions

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.tenmillions.database.AppDatabase
import com.example.tenmillions.database.Question
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.logging.Logger

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        importQuestions(this.applicationContext)

//        val questionDao = AppDatabase.getInstance(this).questionDao()
//
//        // Получить количество вопросов
//        val count = 6
//
//        // Сгенерировать случайное число
//        val randomId = (1 until count).random()
//
//        // Получить вопрос по ID
//        val question = questionDao.getById(randomId)
//        val questionText = findViewById<TextView>(R.id.question_text)
//
//        // Задать текст вопроса
//        questionText.text = question.questionText
//
//        // Найти элементы, где будут отображаться варианты ответа
//        val answer1 = findViewById<TextView>(R.id.answer1)
//        val answer2 = findViewById<TextView>(R.id.answer2)
//        val answer3 = findViewById<TextView>(R.id.answer3)
//        val answer4 = findViewById<TextView>(R.id.answer4)
//
//        // Задать текст вариантов ответа
//        answer1.text = question.answer1
//        answer2.text = question.answer2
//        answer3.text = question.answer3
//        answer4.text = question.answer4
    }

    private fun importQuestions(context: Context) {
        val inputStream = context.assets.open("questions.json")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val json = reader.readText()


        val questions = Gson().fromJson(json, Array<Question>::class.java)

        val db = AppDatabase.getInstance(context)
        val questionDao = db.questionDao()

        for (question in questions) {
            questionDao.insert(question)
        }
    }
}