package ru.app.fefuactivity.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Coordinate(
    val latitude: Double,
    val longitude: Double
)

class CoordinateConverter {
    @TypeConverter
    fun fromCoordinateList(coordinates: List<Coordinate>): String {
        return Gson().toJson(coordinates)
    }

    @TypeConverter
    fun toCoordinateList(value: String): List<Coordinate> {
        val listType = object : TypeToken<List<Coordinate>>() {}.type
        return Gson().fromJson(value, listType)
    }
} 