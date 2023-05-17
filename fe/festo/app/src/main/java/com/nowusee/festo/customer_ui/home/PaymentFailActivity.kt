package com.nowusee.festo.customer_ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.nowusee.festo.R

class PaymentFailActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_fail)

        // 홈으로 이동
        val goHomeBtn = findViewById<Button>(R.id.goHome)

        goHomeBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

}