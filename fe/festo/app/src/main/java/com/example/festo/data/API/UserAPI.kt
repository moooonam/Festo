package com.example.festo.data.API

import com.example.festo.data.res.BoothMenuListRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserAPI {

    // 부스 메뉴 조회. 일단 id 2번 부스로 고정
//    @GET("booths/{booth_id}/orders/?completed=false")
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjIwMjkyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4MDQyOTIsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjIwMjkyfQ.t90tu1tkclmb3eeBV-KKq5V0oyQ15ak-EEm7EFq8ANQ")
    @GET("booths/2/menus")
    fun getBoothMenuList(): Call<List<BoothMenuListRes>>
}