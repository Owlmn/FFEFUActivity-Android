package ru.app.fefuactivity.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserActivityDao {
    @Query("SELECT * FROM user_activities ORDER BY startTime DESC")
    fun getAllActivities(): LiveData<List<UserActivity>>

    @Insert
    suspend fun insertActivity(activity: UserActivity)
} 