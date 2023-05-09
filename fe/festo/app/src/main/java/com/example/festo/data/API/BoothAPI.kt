package com.example.festo.data.API

import com.example.festo.data.req.RegisterBoothReq
import com.example.festo.data.res.BoothOrderListCompleteRes
import com.example.festo.data.res.BoothOrderListRes
import com.example.festo.data.res.RegisterBoothRes
import com.example.festo.data.res.TestUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BoothAPI {
    @POST("festivals/{festival_id}/booths")
    fun registerFestival(@Body data: RegisterBoothReq): Call<RegisterBoothRes>

    @GET("booths/{booth_id}/orders/?completed=false")
    fun getBoothOrderList(): Call<List<BoothOrderListRes>>


    @GET("booths/{booth_id}/orders/?completed=true")
    fun getBoothOrderListComplete(): Call<List<BoothOrderListCompleteRes>>
}