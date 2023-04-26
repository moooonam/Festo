package com.example.festo.customer_ui.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.festo.R
import com.example.festo.customer_ui.search.SearchFragment
import com.example.festo.databinding.ActivityFestivallistBinding

class FestivallistActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFestivallistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFestivallistBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_festivallist)



        }
    }

