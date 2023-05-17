package com.nowusee.festo.host_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nowusee.festo.R
import com.nowusee.festo.booth_ui.mypage.HostMypageFragment
import com.nowusee.festo.databinding.ActivityHostMainBinding
import com.nowusee.festo.host_ui.boothlist.BoothListFragment
import com.nowusee.festo.host_ui.home.HostHomeFragment
import com.nowusee.festo.host_ui.salesanalysis.HostSalesAnalysisFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HostMainActivity: AppCompatActivity() {

    private lateinit var mBinding: ActivityHostMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHostMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_host_main)
        supportFragmentManager.beginTransaction().replace(R.id.host_layout_nav_bottom, HostHomeFragment()).commit()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.host_bottom_nav)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.hostHomeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.host_layout_nav_bottom, HostHomeFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.boothListFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.host_layout_nav_bottom, BoothListFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.hostSalesAnalysisFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.host_layout_nav_bottom, HostSalesAnalysisFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.hostMypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.host_layout_nav_bottom, HostMypageFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }
            true
        }

    }
}