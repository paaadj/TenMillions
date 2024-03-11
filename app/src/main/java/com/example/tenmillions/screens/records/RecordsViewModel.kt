package com.example.tenmillions.screens.records

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tenmillions.database.User
import com.example.tenmillions.database.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecordsViewModel(
    val dao: UserDao,
    application: Application
): AndroidViewModel(application) {
    private lateinit var _usersList: MutableList<User>
    val usersList: List<User>
        get() = _usersList

    private var _usersLoaded = MutableLiveData<Boolean>(false)
    val usersLoaded: LiveData<Boolean>
        get() = _usersLoaded


    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    init {
        uiScope.launch {
            getUsers()
        }
    }

    private suspend fun getUsers(){
        withContext(Dispatchers.IO) {
            _usersList = dao.getAll()
        }
        withContext(Dispatchers.Main) {
            _usersLoaded.value = true
        }
    }
}