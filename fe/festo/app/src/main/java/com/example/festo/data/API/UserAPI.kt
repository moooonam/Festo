package com.example.festo.data.API

import com.example.festo.data.req.LoginReq
import com.example.festo.data.req.MenuRecommendReq
import com.example.festo.data.req.OrderReq
import com.example.festo.data.res.BoothDetailRes
import com.example.festo.data.res.BoothListRes
import com.example.festo.data.res.BoothMenuListRes
import com.example.festo.data.res.BoothRecommendRes
import com.example.festo.data.res.BoothWaitingRes
import com.example.festo.data.res.FestivalInfoRes
import com.example.festo.data.res.FestivalListRes
import com.example.festo.data.res.IsHaveFestivalRes
import com.example.festo.data.res.LoginRes
import com.example.festo.data.res.MenuRecommendRes
import com.example.festo.data.res.MyBoothListRes
import com.example.festo.data.res.UserInfoRes
import com.example.festo.data.res.UserNotificationListRes
import com.example.festo.data.res.UserOrderListRes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {
    @POST("api/v1/login")
    fun login(@Body request: LoginReq): Call<LoginRes>
    companion object { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.
        private const val BASE_URL = "https://k8c106.p.ssafy.io/api/" // 주소
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

    /*@GET("festivals/search")
    fun searchFestival(@Header("Authorization") token: String): Call<List<SearchFestivalRes>>*/


    //유저 알림목록 불러오기
    @GET("notifications")
    fun getUserNotificationData(@Header("Authorization") token: String): Call<List<UserNotificationListRes>>


    // 부스 메뉴 조회. 일단 id 2번 부스로 고정
//    @GET("booths/{booth_id}/orders/?completed=false")
//    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjk0MjAxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4NzgyMDEsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjk0MjAxfQ.AeidYEQ6RWsFYvzh6z1l990YGjAFHCkfiKV85UU2D7E")
    @GET("booths/{booth_id}/menus")
    fun getBoothMenuList(@Header("Authorization") token: String, @Path("booth_id") booth_id: String): Call<List<BoothMenuListRes>>

    // 축제 상세정보 조회
    @GET("festivals/{festival_id}")
    fun getFestivalDetail(@Header("Authorization") token: String, @Path("festival_id") festival_id: String?): Call<FestivalInfoRes>

    // 주문하기
     @POST("orders")
    fun orderMenu(@Header("Authorization") token: String, @Body data: OrderReq) : Call<Void>

    // 부스 리스트 조회
     @GET("festivals/{festival_id}/booths")
    fun getBoothList(@Header("Authorization") token: String, @Path("festival_id") festival_id: String?): Call<List<BoothListRes>>

    // 부스 상세정보 조회
     @GET("booths/{booth_id}")
    fun getBoothDetail(@Header("Authorization") token: String, @Path("booth_id") booth_id: String): Call<BoothDetailRes>

    // 부스 대기인원 조회
    @GET("booths/{booth_id}/waiting")
    fun getBoothWaiting(@Header("Authorization") token: String, @Path("booth_id") booth_id: Long?): Call<BoothWaitingRes>

    // 주문 내역 조회
    @GET("orders")
    fun getOrderList(@Header("Authorization") token: String): Call<List<UserOrderListRes>>


    // 유저 정보 조회
    @GET("api/v1/members/me")
    fun getUserInfo(@Header("Authorization") token: String): Call<UserInfoRes>

    // 축제 여부 조회
    @GET("festival/{member_id}/manager")
    fun getIsHaveFestival(@Header("Authorization")token: String, @Path("member_id") member_id: String): Call<IsHaveFestivalRes>


    // 내가 등록한 부스 리스트 조회
    @GET("booths/{owner_id}/owner")
    fun getMyBoothList(@Header("Authorization") token: String,  @Path("owner_id") owner_id: String): Call<List<MyBoothListRes>>


    // 사용자 주문 기록 기반으로 부스 추천
    @GET("recommend_booth/{festival_id}/{user_id}")
    fun getBoothRecommend(@Header("Authorization") token: String, @Path("festival_id") festival_id: String?, @Path("user_id") user_id: String): Call<List<BoothRecommendRes>>


    // 주문할 메뉴 기반으로 메뉴 추천
    @POST("recommend_order/{festival_id}")
    fun getMenuRecommend(@Header("Authorization") token: String,  @Path("festival_id") festival_id: String, @Body data: MenuRecommendReq): Call<List<MenuRecommendRes>>

}