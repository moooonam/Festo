package com.nowusee.festo.customer_ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nowusee.festo.R
import com.nowusee.festo.databinding.ActivityFestivallistBinding

class FestivallistActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFestivallistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFestivallistBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_festivallist)



        }
    }

