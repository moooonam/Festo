package com.example.festo.customer_ui.home


import android.content.ClipData.Item
import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.mypage.MypageFragment
import com.example.festo.customer_ui.mypage.RecentOrderListData
import com.example.festo.customer_ui.orderlist.OrderlistFragment
import com.example.festo.customer_ui.recent.RecentFragment
import com.example.festo.customer_ui.search.SearchFragment
import com.example.festo.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView



class HomeActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, HomeFragment())
            .commit()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.my_bottom_nav)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, HomeFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, SearchFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.recentFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, RecentFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.orderlistFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, OrderlistFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, MypageFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }
            true
        }


    }


}


