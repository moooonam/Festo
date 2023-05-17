package com.nowusee.festo.customer_ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.nowusee.festo.R
import com.nowusee.festo.customer_ui.search.SearchActivity
import com.nowusee.festo.data.API.UserAPI
import com.nowusee.festo.data.DataRetrofitClient
import com.nowusee.festo.data.res.BoothListRes
import com.nowusee.festo.data.res.BoothRecommendRes
import com.nowusee.festo.data.res.BoothWaitingRes
import com.nowusee.festo.data.res.FestivalInfoRes
import com.nowusee.festo.data.res.UserInfoRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale


class FestivalActivity : AppCompatActivity() {
    private var retrofit = RetrofitClient.client
    private var boothList = emptyList<BoothListRes>()
    private var dataRetrofit = DataRetrofitClient.client
    private var recommendBoothList = emptyList<BoothRecommendRes>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.nowusee.festo.R.layout.activity_festival)
        val festival_id = intent.getStringExtra("festivalId")

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
//                    println("성공!!!!!!!!!!!!!!!!!!!")
//                    println(response.body()?.startDate)
//                    Log.d(" 테스트", "${response.body()}")
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
//                println("실패!!!!!!!!!!!!!!!!!!!")
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
//                    println("성공!!!!!!!!!!!!!!!!!!!")
//                    println(response.body())
//                    Log.d(" 테스트", "${response.body()}")
                    boothList = response.body() ?: emptyList()
//                    부스 리스트 연결
                    val Adapter = BoothAdapter(this@FestivalActivity, boothList, token,
                        festival_id.toString()
                    )
                    val list_view = findViewById<ListView>(com.nowusee.festo.R.id.list_view)
                    list_view.adapter = Adapter

                }
            }

            override fun onFailure(call: Call<List<BoothListRes>>, t: Throwable) {
//                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })

        // 나의 정보 불러오기
        postApi!!.getUserInfo(token).enqueue(object :
            Callback<UserInfoRes> {
            override fun onResponse(
                call: Call<UserInfoRes>,
                response: Response<UserInfoRes>
            ) {
                if (response.isSuccessful) {
//                    println("성공!!!!!!!!!!!!!!!!!!!")
//                    println(response.body())
//                    Log.d("유저정보", "${response.body()}")
                    val userNickname = findViewById<TextView>(R.id.userNickname)

                    // 데이터 xml에 입력
                    userNickname.text = "${response.body()?.nickname}"
                }
            }

            override fun onFailure(call: Call<UserInfoRes>, t: Throwable) {
//                println("유저 정보 조회 실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })

        // 추천 부스 가져오기
        val dataPostApi = dataRetrofit?.create(UserAPI::class.java)
        val memberId = sharedPreferences.getString("memberId", "")
        val myId = "$memberId"
        dataPostApi!!.getBoothRecommend(token, festival_id, myId).enqueue(object :
            Callback<List<BoothRecommendRes>> {
            override fun onResponse(
                call: Call<List<BoothRecommendRes>>,
                response: Response<List<BoothRecommendRes>>
            ) {
                if (response.isSuccessful) {
//                    println("성공!!!!!!!!!!!!!!!!!!!")
//                    println(response.body())
//                    println(festival_id)
//                    Log.d("부스매출분석 데이터 불러오기 성공", "${response.body()}")
                    recommendBoothList = response.body() ?: emptyList()

                    // 추천 부스 연결
                    var recommend1Name = findViewById<TextView>(R.id.recommend1Name)
                    var recommend1Explanation = findViewById<TextView>(R.id.recommend1Explanation)
                    var recommend1Wait = findViewById<TextView>(R.id.recommend1Wait)
                    var recommend1Image = findViewById<ImageView>(R.id.recommend1Image)

                    var recommend2Name = findViewById<TextView>(R.id.recommend2Name)
                    var recommend2Explanation = findViewById<TextView>(R.id.recommend2Explanation)
                    var recommend2Wait = findViewById<TextView>(R.id.recommend2Wait)
                    var recommend2Image = findViewById<ImageView>(R.id.recommend2Image)

                    var recommendText = findViewById<LinearLayout>(R.id.recommendText)
                    var recommend1 = findViewById<CardView>(R.id.recommend1)
                    var recommend2 = findViewById<CardView>(R.id.recommend2)


                    if (recommendBoothList.size  == 0) {
                        recommendText.visibility = View.GONE
                        recommend1.visibility = View.GONE
                        recommend2.visibility = View.GONE
                    } else if (recommendBoothList.size == 1) {
                        postApi!!.getBoothWaiting(token, response.body()?.get(0)?.booth_id?.toLong()).enqueue(object : Callback<BoothWaitingRes> {
                            override fun onResponse(
                                call: Call<BoothWaitingRes>,
                                response: Response<BoothWaitingRes>
                            ) {
                                if (response.isSuccessful) {
//                                    println("대기인원성공!!!!!!!!!!!!!!!!!!!")
//                                    println(response.body()?.waiting)
                                    recommend1Wait.text = response.body()?.waiting.toString()
//                                    Log.d(" 부스대기인원", "${response.body()}")
                                } else {
//                                    Log.d(" 부스대기인원", "실패111111111111")
                                }
                            }

                            override fun onFailure(call: Call<BoothWaitingRes>, t: Throwable) {
//                                Log.d(" 부스대기인원", "실패222222222222222")
                                t.printStackTrace()
                            }
                        })
                        recommendText.visibility = View.VISIBLE
                        recommend1.visibility = View.VISIBLE
                        recommend2.visibility = View.GONE

                        recommend1Name.text = response.body()?.get(0)?.name
                        recommend1Explanation.text = response.body()?.get(0)?.booth_description

                        Glide.with(this@FestivalActivity)
                            .load(response.body()?.get(0)?.image_url)
                            .into(recommend1Image)

                        // 카드뷰 클릭시 detail 페이지로 이동
                        recommend1.setOnClickListener {
                            val intent = Intent(this@FestivalActivity, BoothDetailActivity::class.java)
                            intent.putExtra("festivalId", festival_id)
                            intent.putExtra("boothId", response.body()?.get(0)?.booth_id.toString())
                            this@FestivalActivity.startActivity(intent)
                        }


                    } else if (recommendBoothList.size >= 2) {
                        postApi!!.getBoothWaiting(token, response.body()?.get(0)?.booth_id?.toLong()).enqueue(object : Callback<BoothWaitingRes> {
                            override fun onResponse(
                                call: Call<BoothWaitingRes>,
                                response: Response<BoothWaitingRes>
                            ) {
                                if (response.isSuccessful) {
//                                    println("대기인원성공!!!!!!!!!!!!!!!!!!!")
//                                    println(response.body()?.waiting)
                                    recommend1Wait.text = response.body()?.waiting.toString()
//                                    Log.d(" 부스대기인원", "${response.body()}")
                                } else {
//                                    Log.d(" 부스대기인원", "실패111111111111")
                                }
                            }

                            override fun onFailure(call: Call<BoothWaitingRes>, t: Throwable) {
//                                Log.d(" 부스대기인원", "실패222222222222222")
                                t.printStackTrace()
                            }
                        })
                        postApi!!.getBoothWaiting(token, response.body()?.get(1)?.booth_id?.toLong()).enqueue(object : Callback<BoothWaitingRes> {
                            override fun onResponse(
                                call: Call<BoothWaitingRes>,
                                response: Response<BoothWaitingRes>
                            ) {
                                if (response.isSuccessful) {
//                                    println("대기인원성공!!!!!!!!!!!!!!!!!!!")
//                                    println(response.body()?.waiting)
                                    recommend2Wait.text = response.body()?.waiting.toString()
//                                    Log.d(" 부스대기인원", "${response.body()}")
                                } else {
//                                    Log.d(" 부스대기인원", "실패111111111111")
                                }
                            }

                            override fun onFailure(call: Call<BoothWaitingRes>, t: Throwable) {
//                                Log.d(" 부스대기인원", "실패222222222222222")
                                t.printStackTrace()
                            }
                        })
                        recommendText.visibility = View.VISIBLE
                        recommend1.visibility = View.VISIBLE
                        recommend2.visibility = View.VISIBLE

                        recommend1Name.text = response.body()?.get(0)?.name
                        recommend1Explanation.text = response.body()?.get(0)?.booth_description
                        Glide.with(this@FestivalActivity)
                            .load(response.body()?.get(0)?.image_url)
                            .into(recommend1Image)

                        recommend2Name.text = response.body()?.get(1)?.name
                        recommend2Explanation.text = response.body()?.get(1)?.booth_description
                        Glide.with(this@FestivalActivity)
                            .load(response.body()?.get(1)?.image_url)
                            .into(recommend2Image)

                        // 카드뷰 클릭시 detail 페이지로 이동
                        recommend1.setOnClickListener {
                            val intent = Intent(this@FestivalActivity, BoothDetailActivity::class.java)
                            intent.putExtra("festivalId", festival_id)
                            intent.putExtra("boothId", response.body()?.get(0)?.booth_id.toString())
                            this@FestivalActivity.startActivity(intent)
                        }

                        // 카드뷰 클릭시 detail 페이지로 이동
                        recommend2.setOnClickListener {
                            val intent = Intent(this@FestivalActivity, BoothDetailActivity::class.java)
                            intent.putExtra("festivalId", festival_id)
                            intent.putExtra("boothId", response.body()?.get(1)?.booth_id.toString())
                            this@FestivalActivity.startActivity(intent)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<List<BoothRecommendRes>>, t: Throwable) {
//                println("추천 부스 받아오기 실패!!!!!!!!!!!!!!!!!!!")
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