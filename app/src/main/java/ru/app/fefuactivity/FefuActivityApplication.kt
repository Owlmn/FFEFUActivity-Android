package ru.app.fefuactivity

import android.app.Application
import ru.app.fefuactivity.data.AppDatabase
 
class FefuActivityApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
} 