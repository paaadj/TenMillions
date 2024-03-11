package com.example.tenmillions.screens.game

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tenmillions.database.Question
import com.example.tenmillions.database.QuestionDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class GameViewModel(
    val dao: QuestionDao,
    application: Application,
): AndroidViewModel(application) {

    private var _score = MutableLiveData<Int>(10000)
    val score: LiveData<Int>
        get() = _score

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private lateinit var questionList: MutableList<Question>

    private var _eventGameFinish = MutableLiveData<Boolean>(false)
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private var _questionsLoaded = MutableLiveData<Boolean>(false)
    val questionLoaded: LiveData<Boolean>
        get() = _questionsLoaded

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var timer: CountDownTimer
    private var _secondsUntilEnd = MutableLiveData<Long>()
    val secondsUntilEnd: LiveData<Long>
        get() = _secondsUntilEnd

    companion object {
        const val ONE_SECOND = 1000L
        const val ROUND_TIME = 30 * ONE_SECOND
    }
    init {
        uiScope.launch {
            getNewQuestions()
        }
        _secondsUntilEnd.value = ROUND_TIME / ONE_SECOND
        timer = object : CountDownTimer(ROUND_TIME, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
                _secondsUntilEnd.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                _score.value = 0
                _eventGameFinish.value = true
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun getNewQuestions(){
        withContext(Dispatchers.IO) {
            var locale = Locale.getDefault().language
            if (locale != "ru")
                locale = "en"
            questionList = dao.getRandomItems(locale)
            questionList.shuffle()
        }
        withContext(Dispatchers.Main) {
            _questionsLoaded.value = true
        }
    }

    fun nextQuestion() {
        if (questionList.isEmpty()) {
            uiScope.launch {
                getNewQuestions()
            }
            _eventGameFinish.value = true
        } else {
            _question.value = questionList.removeAt(0)
            timer.cancel()
            timer.start()
        }
    }

    fun onSubmit(points: Int) {
        Log.i("debug", "123123123")
        _score.value = points
        if (points == 0)
            _eventGameFinish.value = true
        else
            nextQuestion()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    fun loadedFinished() {
        Log.i("debug", "123123")
        _questionsLoaded.value = false
    }
}