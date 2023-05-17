package com.nowusee.festo.booth_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nowusee.festo.R
import com.nowusee.festo.booth_ui.home.BoothHomeFragment
import com.nowusee.festo.booth_ui.mypage.BoothMypageFragment
import com.nowusee.festo.booth_ui.orderlist.BoothOrderListFragment
import com.nowusee.festo.booth_ui.salesanalysis.SalesAnalysisFragment
import com.nowusee.festo.databinding.ActivityBoothMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BoothMainActivity : AppCompatActivity(){

    private lateinit var mBinding: ActivityBoothMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBoothMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportFragmentManager.beginTransaction().replace(R.id.booth_layout_nav_bottom, BoothHomeFragment()).commit()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.booth_bottom_nav)


        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.boothHomeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.booth_layout_nav_bottom, BoothHomeFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.boothmypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.booth_layout_nav_bottom, BoothMypageFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.boothOderlistFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.booth_layout_nav_bottom, BoothOrderListFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.boothSalesAnalysisFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.booth_layout_nav_bottom, SalesAnalysisFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }
            true
        }
    }
}