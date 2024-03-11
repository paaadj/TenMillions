package com.example.tenmillions.screens.score

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tenmillions.database.UserDao

class ScoreViewModelFactory(
    private val dao: UserDao,
    private val application: Application,
    private val finalScore: Int,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(dao, application, finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}