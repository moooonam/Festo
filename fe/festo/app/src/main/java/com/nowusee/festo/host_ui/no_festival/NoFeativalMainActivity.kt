package com.nowusee.festo.host_ui.no_festival

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nowusee.festo.R

class NoFeativalMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_featival_main)

        supportFragmentManager.beginTransaction().replace(R.id.no_festival_layout_nav_bottom, NoFestivalMainFragment()).commit()
    }
}