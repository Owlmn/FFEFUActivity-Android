package ru.app.fefuactivity.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "user_activities")
@TypeConverters(ActivityTypeConverter::class, CoordinateConverter::class)
data class UserActivity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: ActivityType,
    val startTime: Date,
    val endTime: Date,
    val coordinates: List<Coordinate>
) 