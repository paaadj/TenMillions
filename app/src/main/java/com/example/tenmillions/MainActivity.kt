package com.example.tenmillions

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.tenmillions.database.Question

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        startGame()
    }

    private fun startGame() {
        val questionText = findViewById<TextView>(R.id.questionText)
        val answer1 = findViewById<TextView>(R.id.answer1text)
        val answer2 = findViewById<TextView>(R.id.answer2text)
        val answer3 = findViewById<TextView>(R.id.answer3text)
        val answer4 = findViewById<TextView>(R.id.answer4text)
        val points1 = findViewById<EditText>(R.id.answer1Points)
        val points2 = findViewById<EditText>(R.id.answer2Points)
        val points3 = findViewById<EditText>(R.id.answer3Points)
        val points4 = findViewById<EditText>(R.id.answer4Points)

        var currentQuestion = Question(
            1, "кто", "correct", "1", "2", "3"
        )

        questionText.text = currentQuestion.questionText
        answer1.text = currentQuestion.correctAnswer
        answer2.text = currentQuestion.answer1
        answer3.text = currentQuestion.answer2
        answer4.text = currentQuestion.answer3
    }
}