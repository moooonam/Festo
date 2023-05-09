package com.example.festo.data.API

import com.example.festo.data.req.RegisterBoothReq
import com.example.festo.data.res.RegisterBoothRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BoothAPI {
    @POST("festivals/{festival_id}/booths")
    fun registerFestival(@Body data: RegisterBoothReq): Call<RegisterBoothRes>
}