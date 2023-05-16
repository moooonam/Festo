package com.example.festo.customer_ui.home

import MenuAdapter
import android.content.Context
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
import com.example.festo.data.res.BoothDetailRes
import com.example.festo.data.res.BoothMenuListRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale


class BoothDetailActivity : AppCompatActivity() {
    private var retrofit = RetrofitClient.client
    private var putBoothName : String = "dd"
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
        val festivalId = intent.getStringExtra("festivalId")

        // 부스 상세정보 retrofit
        val postApi = retrofit?.create(UserAPI::class.java)
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        postApi!!.getBoothDetail(token, boothId.toString()).enqueue(object : Callback<BoothDetailRes> {
            override fun onResponse(
                call: Call<BoothDetailRes>,
                response: Response<BoothDetailRes>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body())
                    Log.d(" 테스트", "${response.body()}")
                    val boothName = findViewById<TextView>(R.id.boothName)
                    val boothLocation = findViewById<TextView>(R.id.boothLocation)
                    val boothExplanation = findViewById<TextView>(R.id.boothExplanation)

                    // 데이터 xml에 입력
                    boothName.text = response.body()?.name
                    boothLocation.text = response.body()?.locationDescription
                    boothExplanation.text = response.body()?.boothDescription
                    putBoothName = response.body()?.name.toString()
                }
            }

            override fun onFailure(call: Call<BoothDetailRes>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })

        // 부스 메뉴 리스트 retrofit
        postApi!!.getBoothMenuList(token, boothId.toString()).enqueue(object : Callback<List<BoothMenuListRes>> {
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
                intent.putExtra("festivalId", festivalId)
                intent.putExtra("boothName", putBoothName)
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

    override fun onResume() {
        super.onResume()
        // 이전 페이지로 돌아왔을 때 myOrderList를 비워줌
        myOrderList.clear()
    }

}