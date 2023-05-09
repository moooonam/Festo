package com.example.festo.data.API

import com.example.festo.data.req.RegisterFestivalReq
import com.example.festo.data.res.RegisterFestivalRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HostAPI {
    @POST("festivals")
    fun registerFestival(@Body data: RegisterFestivalReq): Call<RegisterFestivalRes>
}