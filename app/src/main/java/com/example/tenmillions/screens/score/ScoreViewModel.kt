package com.example.tenmillions.screens.score

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tenmillions.database.User
import com.example.tenmillions.database.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScoreViewModel(
    val dao: UserDao,
    application: Application,
    finalScore: Int
): AndroidViewModel(application) {

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _score.value = finalScore
    }

    fun save(username: String) {
        uiScope.launch {
            saveUser(username)
        }
    }

    private suspend fun saveUser(username: String) {
        withContext(Dispatchers.IO) {
            val user = User(username, _score.value!!)
            dao.insert(user)
        }
    }

    fun status(username: String, callback: (Boolean) -> Unit) {
        uiScope.launch {
            val stat = usernameStatus(username)
            Log.i("debug", "$stat")
            callback(stat)
        }
    }

    private suspend fun usernameStatus(username: String): Boolean{
        return withContext(Dispatchers.IO) {
            dao.checkUsername(username)
        }
    }
}