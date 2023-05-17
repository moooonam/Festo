package com.nowusee.festo.booth_ui.no_booth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nowusee.festo.R

class NoBoothMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_booth_main)

        supportFragmentManager.beginTransaction().replace(R.id.no_booth_layout_nav_bottom, NoBoothMainFragment()).commit()
    }
}