package com.example.festo.data.API

import com.example.festo.data.req.RegiFestivalRequest
import com.example.festo.data.req.RegisterFestivalReq
import com.example.festo.data.req.TestReq
import com.example.festo.data.res.RegisterFestivalRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface HostAPI {
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjc2ODM1LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4NjA4MzUsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjc2ODM1fQ.n6qr--t-M8jAwr7xkM5ndhd54Sd9TsLnt-lQ3Dj7J2Y")
    @Multipart
    @POST("festivals")
    fun registerFestival(@Part("request") request: RegiFestivalRequest, @Part festivalImg: MultipartBody.Part): Call<Long>
}
