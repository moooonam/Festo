package com.example.festo.customer_ui.home


import android.content.ClipData.Item
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.festo.R
import com.example.festo.customer_ui.mypage.MypageFragment
import com.example.festo.customer_ui.orderlist.OrderlistFragment
import com.example.festo.customer_ui.recent.RecentFragment
import com.example.festo.customer_ui.search.SearchFragment
import com.example.festo.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class HomeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, HomeFragment()).commit()
        // 네비게이션들을 담는 호스트
        val TAG: String = "HomeActivity : "
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.my_bottom_nav)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, HomeFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, SearchFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.recentFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, RecentFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.orderlistFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, OrderlistFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, MypageFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }
            true
        }


    }





}


