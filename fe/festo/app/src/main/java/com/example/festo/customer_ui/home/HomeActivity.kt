package com.example.festo.customer_ui.home


import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.mypage.MypageFragment
import com.example.festo.customer_ui.orderlist.OrderlistFragment
import com.example.festo.customer_ui.recent.RecentFragment
import com.example.festo.customer_ui.search.SearchFragment
import com.example.festo.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    // 페스티벌 더미 리스트
    var FestiList = mutableListOf<FestiList>(
        FestiList(R.drawable.festival1, "진주 남강유등축제"),
        FestiList(R.drawable.festival2, "광양 전통숯불구이축제"),
        FestiList(R.drawable.festival1, "진주 남강유등축제"),
        FestiList(R.drawable.festival2, "광양 전통숯불구이축제"),
    )

    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)

        val Adapter = FestListAdapter(this, FestiList)
        val list_view = findViewById<ListView>(R.id.festivalListView)
        list_view.adapter = Adapter
        // 고른 축제 페이지 연결

        supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, HomeFragment())
            .commit()
        // 네비게이션들을 담는 호스트
        val TAG: String = "HomeActivity : "
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


