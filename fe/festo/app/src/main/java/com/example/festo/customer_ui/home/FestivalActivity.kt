package com.example.festo.customer_ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.search.SearchActivity
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.BoothListRes
import com.example.festo.data.res.FestivalInfoRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale


class FestivalActivity : AppCompatActivity() {
    private var retrofit = RetrofitClient.client
    private var boothList = emptyList<BoothListRes>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.festo.R.layout.activity_festival)
        val festival_id = intent.getStringExtra("festivalId")

        // 하나의 축제에 대한 부스 리스트 어댑터 연결
//        val Adapter = BoothAdapter(this, BoothList)
//        val list_view = findViewById<ListView>(com.example.festo.R.id.list_view)
//        list_view.adapter = Adapter

        val notificationBtn = findViewById<ImageView>(R.id.notification_btn)
        notificationBtn.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("fragment", "NotificationFragment")
            startActivity(intent)
        }

        // 축제 상세정보 조회
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        val postApi = retrofit?.create(UserAPI::class.java)
        postApi!!.getFestivalDetail(token, festival_id).enqueue(object : Callback<FestivalInfoRes> {
            override fun onResponse(
                call: Call<FestivalInfoRes>,
                response: Response<FestivalInfoRes>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body()?.startDate)
                    Log.d(" 테스트", "${response.body()}")
                    val festivalName = findViewById<TextView>(R.id.festivalName)
                    val festivalAddress = findViewById<TextView>(R.id.festivalAddress)
                    val festivalPeriod = findViewById<TextView>(R.id.festivalPeriod)

                    // 날짜 형식 변환
                    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
                    val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

                    val startDateString = response.body()?.startDate
                    val startDate = inputFormat.parse(startDateString.toString())
                    val formattedStartDate = outputFormat.format(startDate)

                    val endDateString = response.body()?.endDate
                    val endDate = inputFormat.parse(endDateString.toString())
                    val formattedEndDate = outputFormat.format(endDate)

                    // 데이터 xml에 입력
                    festivalName.text = response.body()?.name
                    festivalAddress.text = response.body()?.address
                    festivalPeriod.text = "${formattedStartDate} ~ ${formattedEndDate}"
                }
            }

            override fun onFailure(call: Call<FestivalInfoRes>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })


        // 부스 리스트 조회
        postApi!!.getBoothList(token, festival_id).enqueue(object : Callback<List<BoothListRes>> {
            override fun onResponse(
                call: Call<List<BoothListRes>>,
                response: Response<List<BoothListRes>>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body())
                    Log.d(" 테스트", "${response.body()}")
                    boothList = response.body() ?: emptyList()
//                    부스 리스트 연결
                    val Adapter = BoothAdapter(this@FestivalActivity, boothList, token)
                    val list_view = findViewById<ListView>(com.example.festo.R.id.list_view)
                    list_view.adapter = Adapter

                }
            }

            override fun onFailure(call: Call<List<BoothListRes>>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })


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