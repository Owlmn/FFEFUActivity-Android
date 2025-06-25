package ru.app.fefuactivity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.app.fefuactivity.data.*

class UserActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserActivityRepository
    val allActivities: LiveData<List<UserActivity>>

    init {
        val dao = AppDatabase.getDatabase(application).userActivityDao()
        repository = UserActivityRepository(dao)
        allActivities = repository.allActivities
    }

    fun insertActivity(activity: UserActivity) = viewModelScope.launch {
        repository.insert(activity)
    }
} 