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
}