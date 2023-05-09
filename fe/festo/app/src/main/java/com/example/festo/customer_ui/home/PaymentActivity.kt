package com.example.festo.customer_ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class PaymentActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // 알림으로 이동
        val notificationBtn = findViewById<ImageView>(R.id.notification_btn)
        notificationBtn.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("fragment", "NotificationFragment")
            startActivity(intent)
        }

        // 선택된 메뉴 객체 전달받음
        val myOrderList = intent.getSerializableExtra("myOrderList") as ArrayList<*>

        // 어댑터 연결
        var Adapter = MyOrderListAdapter(this, myOrderList as ArrayList<MyOrderList>)
        val list_view = findViewById<ListView>(com.example.festo.R.id.list_view)
        list_view.adapter = Adapter

        // 추천 메뉴
        var RecommendMenuDataList : ArrayList <RecommendMenu> = arrayListOf(
            RecommendMenu(R.drawable.logo1,"추천메뉴1",4000),
            RecommendMenu(R.drawable.logo2,"추천메뉴2",2000),
        )

        // 추천메뉴 어댑터 연결
        val Adapter2 = RecommendMenuAdapter(this, RecommendMenuDataList, myOrderList)
        val list_view2 = findViewById<ListView>(R.id.recommend_list_view)
        list_view2.adapter = Adapter2

        // 총합
        var totalPrice: Int = 0
        for (order in myOrderList) {
            totalPrice += order.price * order.cnt
        }
        val total = findViewById<TextView>(com.example.festo.R.id.totalPrice)
        total.text = "${totalPrice.toString()}원"

        // 토스페이먼츠 연결
        val payBtn = findViewById<TextView>(R.id.payBtn)
        payBtn.setOnClickListener {
            val intent = Intent(this, TosspayActivity::class.java)
            intent.putExtra("totalPrice", totalPrice)
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

                R.id.recentFragment -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("fragment", "RecentFragment")
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
