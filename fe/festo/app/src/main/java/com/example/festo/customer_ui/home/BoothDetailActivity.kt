package com.example.festo.customer_ui.home

import MenuAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class BoothDetailActivity : AppCompatActivity() {
    // 예시데이터 정의
    var Menulist = arrayListOf<Menu>(
        Menu(com.example.festo.R.drawable.logo1, "이름1", 1000, false, 0),
        Menu(com.example.festo.R.drawable.logo2, "이름2", 4000, false, 0),
        Menu(com.example.festo.R.drawable.logo3, "이름3", 3000, false, 0),
        Menu(com.example.festo.R.drawable.logo1, "이름1", 2000, false, 0),
        Menu(com.example.festo.R.drawable.logo2, "이름2", 1000, false, 0),
        Menu(com.example.festo.R.drawable.logo3, "이름3", 5000, false, 0),
        Menu(com.example.festo.R.drawable.logo1, "이름1", 1000, false, 0),
        Menu(com.example.festo.R.drawable.logo2, "이름2", 1000, false, 0),
        Menu(com.example.festo.R.drawable.logo3, "이름3", 1000, false, 0),
    )

    // 주문할 메뉴를 담을 리스트
    var myOrderList = arrayListOf<MyOrderList>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.festo.R.layout.activity_booth_detail)

        // 알림으로 이동
        val notificationBtn = findViewById<ImageView>(R.id.notification_btn)
        notificationBtn.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("fragment", "NotificationFragment")
            startActivity(intent)
        }

        // 전달받은 부스 정보
        val intent = intent //전달할 데이터를 받을 Intent
        val boothInfo = intent.getStringExtra("boothInfo")


        // 메뉴 리스트 연결
        val adapter = MenuAdapter(this, Menulist)
        adapter.totalTextView = findViewById(R.id.totalTextView)
        val list_view = findViewById<ListView>(com.example.festo.R.id.menu_list_view)
        list_view.adapter = adapter

        // 합계 출력 id 연결
        adapter.totalTextView = findViewById(R.id.totalTextView)

        // 결제 페이지로 이동
        val payBtn = findViewById<TextView>(R.id.payBtn)
        payBtn.setOnClickListener {
            for (menu in Menulist) {
                if (menu.check && menu.cnt != 0) {
                    val myOrderItem = MyOrderList(menu.image, menu.name, menu.price, menu.cnt)
                    myOrderList.add(myOrderItem)
                }
            }

            if (myOrderList.size != 0) {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("myOrderList", myOrderList)
                startActivity(intent)
            } else {
                Toast.makeText(this, "메뉴를 담아주세요!", Toast.LENGTH_SHORT).show()
            }

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