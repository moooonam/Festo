package com.example.festo.data.API

import com.example.festo.data.req.TestReq
import com.example.festo.data.res.TestRes
import com.example.festo.data.res.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @POST("users")
    fun postTest(@Body USERS: TestReq): Call<TestRes>
}