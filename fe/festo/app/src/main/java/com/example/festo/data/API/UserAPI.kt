package com.example.festo.data.API

import android.content.Context
import com.example.festo.data.req.LoginReq
import com.example.festo.data.res.FestivalListRes
import com.example.festo.data.res.LoginRes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import com.example.festo.data.res.BoothOrderListRes
import com.example.festo.data.res.TestGetUserDataRes
import com.example.festo.data.res.UserNotificationListRes
import com.example.festo.data.req.OrderReq
import com.example.festo.data.res.BoothMenuListRes
import com.example.festo.data.res.FestivalInfoRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserAPI {
    @POST("api/v1/login")
    fun login(@Body request: LoginReq): Call<LoginRes>
    companion object { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.
        private const val BASE_URL = "http://k8c106.p.ssafy.io:8080/" // 주소
        fun create(): UserAPI {
            val gson : Gson =   GsonBuilder().setLenient().create();
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(UserAPI::class.java)
        }
    }

    @GET("festivals")
    fun getFestivalList(@Header("Authorization") token: String): Call<List<FestivalListRes>>

    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjc2ODM1LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4NjA4MzUsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjc2ODM1fQ.n6qr--t-M8jAwr7xkM5ndhd54Sd9TsLnt-lQ3Dj7J2Y")
    @GET("api/v1/members/me")
    fun getUserData(): Call<TestGetUserDataRes>

    //유저 알림목록 불러오기
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjc2ODM1LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4NjA4MzUsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjc2ODM1fQ.n6qr--t-M8jAwr7xkM5ndhd54Sd9TsLnt-lQ3Dj7J2Y")
    @GET("notifications")
    fun getUserNotificationData(): Call<UserNotificationListRes>


    // 부스 메뉴 조회. 일단 id 2번 부스로 고정
//    @GET("booths/{booth_id}/orders/?completed=false")
//    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjk0MjAxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4NzgyMDEsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjk0MjAxfQ.AeidYEQ6RWsFYvzh6z1l990YGjAFHCkfiKV85UU2D7E")
    @GET("booths/3/menus")
    fun getBoothMenuList(@Header("Authorization") token: String): Call<List<BoothMenuListRes>>

    // 축제 상세정보 조회. 일단 축제 1번으로 고정
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjk0MjAxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4NzgyMDEsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjk0MjAxfQ.AeidYEQ6RWsFYvzh6z1l990YGjAFHCkfiKV85UU2D7E")
    @GET("festivals/2")
    fun getFestivalDetail(): Call<FestivalInfoRes>

    // 주문하기
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjk0MjAxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4NzgyMDEsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjk0MjAxfQ.AeidYEQ6RWsFYvzh6z1l990YGjAFHCkfiKV85UU2D7E")
    @POST("orders")
    fun orderMenu(@Body data: OrderReq) : Call<Void>

}