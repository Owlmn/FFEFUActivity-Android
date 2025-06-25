package ru.app.fefuactivity.data

import androidx.lifecycle.LiveData

class UserActivityRepository(private val userActivityDao: UserActivityDao) {
    val allActivities: LiveData<List<UserActivity>> = userActivityDao.getAllActivities()

    suspend fun insert(activity: UserActivity) {
        userActivityDao.insertActivity(activity)
    }
} 