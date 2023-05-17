package com.nowusee.festo.data.API

import com.nowusee.festo.data.req.TestReq
import com.nowusee.festo.data.res.TestRes
import com.nowusee.festo.data.res.TestUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<TestUser>>

    @POST("users")
    fun postTest(@Body USERS: TestReq): Call<TestRes>
}