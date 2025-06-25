package ru.app.fefuactivity.data

import androidx.room.TypeConverter

enum class ActivityType {
    BICYCLE,
    RUNNING,
    WALKING
}

class ActivityTypeConverter {
    @TypeConverter
    fun fromActivityType(activityType: ActivityType): String {
        return activityType.name
    }

    @TypeConverter
    fun toActivityType(value: String): ActivityType {
        return ActivityType.valueOf(value)
    }
} 