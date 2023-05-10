package com.example.festo.customer_ui.home

import MenuAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.search.SearchActivity
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.BoothMenuListRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BoothDetailActivity : AppCompatActivity() {
    private var retrofit = RetrofitClient.client
    private var menuList = emptyList<BoothMenuListRes>()

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
        val boothId = intent.getStringExtra("boothId")

        // 부스 메뉴 리스트 retrofit
        val postApi = retrofit?.create(UserAPI::class.java)
        val tokenValue = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNzA0OTk3LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4ODg5OTcsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNzA0OTk3fQ.ICpCfIDKTJfIoromaX08iMvbNM2R26D3jZboNfewomU"
        val token  = "Bearer $tokenValue"
        postApi!!.getBoothMenuList(token).enqueue(object : Callback<List<BoothMenuListRes>> {
            override fun onResponse(
                call: Call<List<BoothMenuListRes>>,
                response: Response<List<BoothMenuListRes>>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body())
                    Log.d(" 테스트", "${response.body()?.get(0)?.cnt}")
                    menuList = response.body() ?: emptyList()
                    // 메뉴 리스트 연결
                    val adapter = MenuAdapter(this@BoothDetailActivity, menuList)
                    adapter.totalTextView = findViewById(R.id.totalTextView)
                    val list_view = findViewById<ListView>(R.id.menu_list_view)
                    list_view.adapter = adapter
                    adapter.totalTextView = findViewById(R.id.totalTextView)

                }
            }

            override fun onFailure(call: Call<List<BoothMenuListRes>>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })

        // 메뉴 리스트 연결
//        val adapter = MenuAdapter(this, arrayListOf())
//        adapter.totalTextView = findViewById(R.id.totalTextView)
//        val list_view = findViewById<ListView>(com.example.festo.R.id.menu_list_view)
//        list_view.adapter = adapter

        // 합계 출력 id 연결
//        adapter.totalTextView = findViewById(R.id.totalTextView)

        // 결제 페이지로 이동
        val payBtn = findViewById<TextView>(R.id.payBtn)
        payBtn.setOnClickListener {
            for (menu in menuList) {
                if (menu.check && menu.cnt != 0) {
                    val myOrderItem = MyOrderList(menu.productId, menu.imageUrl, menu.name, menu.price, menu.cnt)
                    myOrderList.add(myOrderItem)
                }
            }

            if (myOrderList.size != 0) {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("myOrderList", myOrderList)
                intent.putExtra("boothId", boothId)
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