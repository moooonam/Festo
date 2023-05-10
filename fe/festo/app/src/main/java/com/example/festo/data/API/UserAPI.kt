package com.example.festo.data.API

import com.example.festo.data.res.BoothOrderListRes
import com.example.festo.data.res.TestGetUserDataRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserAPI {
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjc2ODM1LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4NjA4MzUsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjc2ODM1fQ.n6qr--t-M8jAwr7xkM5ndhd54Sd9TsLnt-lQ3Dj7J2Y")
    @GET("api/v1/members/me")
    fun getUserData(): Call<TestGetUserDataRes>
}