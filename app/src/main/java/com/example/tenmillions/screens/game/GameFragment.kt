package com.example.tenmillions.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tenmillions.R
import com.example.tenmillions.database.AppDatabase
import com.example.tenmillions.database.Question
import com.example.tenmillions.databinding.GameFragmentBinding
import kotlin.random.Random

class GameFragment: Fragment() {

    private lateinit var binding: GameFragmentBinding
    private lateinit var viewModel: GameViewModel
    private var correctAnswerIndex: Int = 0
    private var displayedScore: Int = 10000
    private var isProgrammaticTextChange = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).questionDao()
        val viewModelFactory = GameViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]

        viewModel.question.observe(viewLifecycleOwner, Observer { newQuestion ->
            updateQuestion(newQuestion)
        })

        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            val text = resources.getString(R.string.currentPoints, newScore)
            binding.currentPoints.text = text
        })

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(
                    GameFragmentDirections.actionGameFragmentToScoreFragment(
                        viewModel.score.value!!
                    )
                )
                viewModel.onGameFinishComplete()
            }
        })

        binding.submitAnswerButton.setOnClickListener {
            submitAnswer()
            val zeroPoints = resources.getString(R.string.pointsSetText, 0)
            binding.answer1Points.setText(zeroPoints)
            binding.answer2Points.setText(zeroPoints)
            binding.answer3Points.setText(zeroPoints)
            binding.answer4Points.setText(zeroPoints)
        }

        binding.answer1Points.doOnTextChanged { text, _, _, _ ->
            if (!isProgrammaticTextChange) {
                checkTextChanged(binding.answer1Points, text)
                updateScore(binding.answer1Points)
            }
        }
        binding.answer2Points.doOnTextChanged { text, _, _, _ ->
            if (!isProgrammaticTextChange) {
                checkTextChanged(binding.answer2Points, text)
                updateScore(binding.answer2Points)
            }
        }
        binding.answer3Points.doOnTextChanged { text, _, _, _ ->
            if (!isProgrammaticTextChange) {
                checkTextChanged(binding.answer3Points, text)
                updateScore(binding.answer3Points)
            }
        }
        binding.answer4Points.doOnTextChanged { text, _, _, _ ->
            if (!isProgrammaticTextChange) {
                checkTextChanged(binding.answer4Points, text)
                updateScore(binding.answer4Points)
            }
        }

        viewModel.questionLoaded.observe(viewLifecycleOwner, Observer {isLoaded ->
            if (isLoaded) {
                viewModel.nextQuestion()
                viewModel.loadedFinished()
            }

        })

        viewModel.secondsUntilEnd.observe(viewLifecycleOwner, Observer { secondsUntilEnd ->
            val text = resources.getString(R.string.timerText, DateUtils.formatElapsedTime(secondsUntilEnd))
            binding.timerText.text = text
        })

        return binding.root
    }

    private fun checkTextChanged(editText: EditText, text: CharSequence?) {
        if (text.isNullOrBlank()) {
            isProgrammaticTextChange = true
            editText.setText("0")
            isProgrammaticTextChange = false
            editText.setSelection(editText.text.length)
        }
        else if (text.length != 1 && text.startsWith("0")) {
            val newText = resources.getString(
                R.string.pointsSetText,
                editText.text.delete(0, 1).toString().toInt()
            )
            isProgrammaticTextChange = true
            editText.setText(newText)
            isProgrammaticTextChange = false
            editText.setSelection(editText.text.length)
        }
    }

    private fun updateQuestion(newQuestion: Question) {
        val answers: MutableList<String?> = mutableListOf(
            newQuestion.answer1,
            newQuestion.answer2,
            newQuestion.answer3,
        )
        answers.shuffle()
        val randomIndex = Random.nextInt(0, 4)

        answers.add(randomIndex, newQuestion.correctAnswer)
        correctAnswerIndex = randomIndex

        binding.questionText.text = newQuestion.questionText
        binding.answer1text.text = answers[0]
        binding.answer2text.text = answers[1]
        binding.answer3text.text = answers[2]
        binding.answer4text.text = answers[3]
        val text = resources.getString(R.string.currentPoints, viewModel.score.value)
        binding.currentPoints.text = text
    }

    private fun submitAnswer(){
        val points = mutableListOf(
            binding.answer1Points.text.toString().toInt(),
            binding.answer2Points.text.toString().toInt(),
            binding.answer3Points.text.toString().toInt(),
            binding.answer4Points.text.toString().toInt(),
        )
        viewModel.onSubmit(points[correctAnswerIndex])
    }

    private fun updateScore(editText: EditText) {
        val points1 = binding.answer1Points.text.toString().toInt()
        val points2 = binding.answer2Points.text.toString().toInt()
        val points3 = binding.answer3Points.text.toString().toInt()
        val points4 = binding.answer4Points.text.toString().toInt()
        displayedScore = viewModel.score.value.toString().toInt() - points1 - points2 - points3 - points4
        if (displayedScore < 0) {
            val newPoints = (editText.text.toString().toInt() + displayedScore).coerceAtLeast(0)
            val text =
                resources.getString(
                    R.string.pointsSetText, newPoints
                )
            displayedScore = 0
            isProgrammaticTextChange = true
            editText.setText(text)
            isProgrammaticTextChange = false
            editText.setSelection(editText.text.length)
        }
        binding.submitAnswerButton.isEnabled = (displayedScore <= 0)
        val text = resources.getString(R.string.currentPoints, displayedScore)
        binding.currentPoints.text = text
    }
}