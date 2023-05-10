package com.example.festo.data.API

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