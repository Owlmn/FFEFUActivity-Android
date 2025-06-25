package ru.app.fefuactivity.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.app.fefuactivity.R
import android.widget.TextView

class ActivityDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_details)

        findViewById<TextView>(R.id.type).text = intent.getStringExtra("type")
        findViewById<TextView>(R.id.username).text = intent.getStringExtra("username")
        findViewById<TextView>(R.id.distance).text = intent.getStringExtra("distance")
        findViewById<TextView>(R.id.date).text = intent.getStringExtra("date")
        findViewById<TextView>(R.id.duration).text = intent.getStringExtra("duration")
        findViewById<TextView>(R.id.start).text = intent.getStringExtra("start")
        findViewById<TextView>(R.id.finish).text = intent.getStringExtra("finish")
        findViewById<TextView>(R.id.comment).text = intent.getStringExtra("comment")

        findViewById<android.widget.ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }
    }
} 