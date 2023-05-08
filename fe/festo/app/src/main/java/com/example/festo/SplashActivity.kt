package com.example.festo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        },1000)
    }
}