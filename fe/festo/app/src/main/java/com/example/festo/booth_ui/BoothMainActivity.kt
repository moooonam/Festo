package com.example.festo.booth_ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.booth_ui.home.BoothHomeFragment
import com.example.festo.booth_ui.mypage.BoothMypageFragment
import com.example.festo.booth_ui.orderlist.BoothOrderListFragment
import com.example.festo.booth_ui.salesanalysis.SalesAnalysisFragment
import com.example.festo.customer_ui.home.HomeFragment
import com.example.festo.databinding.ActivityBoothMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BoothMainActivity : AppCompatActivity(){

    private lateinit var mBinding: ActivityBoothMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBoothMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportFragmentManager.beginTransaction().replace(R.id.booth_layout_nav_bottom, BoothHomeFragment()).commit()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.booth_bottom_nav)

// 전달받은 부스 아이디
        val boothId = intent.getLongExtra("boothId", 0L)
        Log.d("부스아이디111111111111", "$boothId")

//        val BoothHome = BoothHomeFragment.newInstance(boothId)
        val BoothOrderList = BoothOrderListFragment.newInstance(boothId)
        val BoothMypage = BoothMypageFragment.newInstance(boothId)
        val SalesAnalysis = SalesAnalysisFragment.newInstance(boothId)

// 모든 프래그먼트에 부스 아이디 전달
//        val bundle = Bundle()
//        bundle.putLong("boothId", boothId)
//
//        val fragment1 = BoothHomeFragment()
//        fragment1.arguments = bundle
//
//        val fragment2 = BoothMypageFragment()
//        fragment2.arguments = bundle
//
//        val fragment3 = BoothOrderListFragment()
//        fragment3.arguments = bundle
//
//        val fragment4 = SalesAnalysisFragment()
//        fragment4.arguments = bundle


        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.boothHomeFragment -> {
                    val bundle = Bundle()
                    bundle.putString("boothId", boothId.toString())
                    val fragmentA = BoothHomeFragment()
                    fragmentA.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.booth_layout_nav_bottom, fragmentA).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.boothmypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.booth_layout_nav_bottom, BoothMypage).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.boothOderlistFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.booth_layout_nav_bottom, BoothOrderList).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.boothSalesAnalysisFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.booth_layout_nav_bottom, SalesAnalysis).commit()
                    return@setOnItemSelectedListener true
                }
            }
            true
        }
    }
}