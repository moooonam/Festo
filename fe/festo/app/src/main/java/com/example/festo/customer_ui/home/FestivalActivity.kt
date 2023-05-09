package com.example.festo.customer_ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class FestivalActivity : AppCompatActivity() {
    // 예시 데이터 정의
    var BoothList = arrayListOf<Booth>(
        Booth(R.drawable.logo1, "이름1", "카테고리1", "엄청맛있어욥", "5", "10", "12"),
        Booth(R.drawable.logo2, "이름2", "카테고리2", "우와아아아아", "7", "25", "27"),
        Booth(R.drawable.logo3, "이름3", "카테고리3", "잠온다", "1", "5", "10"),
        Booth(R.drawable.logo1, "이름1", "카테고리1", "엄청맛있어욥", "5", "10", "12"),
        Booth(R.drawable.logo2, "이름2", "카테고리2", "우와아아아아", "7", "25", "27"),
        Booth(R.drawable.logo3, "이름3", "카테고리3", "잠온다", "1", "5", "10"),
        Booth(R.drawable.logo1, "이름1", "카테고리1", "엄청맛있어욥", "5", "10", "12"),
        Booth(R.drawable.logo2, "이름2", "카테고리2", "우와아아아아", "7", "25", "27"),
        Booth(R.drawable.logo3, "이름3", "카테고리3", "잠온다", "1", "5", "10"),
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.festo.R.layout.activity_festival)

        // 하나의 축제에 대한 부스 리스트 어댑터 연결
        val Adapter = BoothAdapter(this, BoothList)
        val list_view = findViewById<ListView>(com.example.festo.R.id.list_view)
        list_view.adapter = Adapter

        val notificationBtn = findViewById<ImageView>(R.id.notification_btn)
        notificationBtn.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("fragment", "NotificationFragment")
            startActivity(intent)
        }


        // 네비게이션
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.my_bottom_nav)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("fragment", "HomeFragment")
                    startActivity(intent)
                }

                R.id.searchFragment -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                }

                R.id.orderlistFragment -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("fragment", "OrderlistFragment")
                    startActivity(intent)
                }

                R.id.mypageFragment -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("fragment", "MypageFragment")
                    startActivity(intent)
                }
            }
            true
        }
    }
}