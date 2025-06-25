package ru.app.fefuactivity.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.app.fefuactivity.R
import android.content.Intent

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<android.widget.ImageView>(R.id.back_button2).setOnClickListener {
            finish()
        }
        findViewById<com.google.android.material.button.MaterialButton>(R.id.login_btn).setOnClickListener {
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
        val rootView = findViewById<android.view.View>(R.id.main)
        val spacer = findViewById<android.view.View>(R.id.keyboardSpacer)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            spacer.layoutParams.height = if (keypadHeight > screenHeight * 0.15) keypadHeight else 0
            spacer.requestLayout()
        }
    }
}