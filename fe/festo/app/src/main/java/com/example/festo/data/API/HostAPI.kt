package com.example.festo.data.API

import com.example.festo.data.req.RegiFestivalRequest
import com.example.festo.data.req.RegisterFestivalReq
import com.example.festo.data.req.TestReq
import com.example.festo.data.res.RegisterBoothRes
import com.example.festo.data.res.RegisterFestivalRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface HostAPI {

    //축제 등록
    @Multipart
    @POST("festivals")
    fun registerFestival(
        @Header("Authorization") token: String,
        @Part("request") request: RegiFestivalRequest,
        @Part festivalImg: MultipartBody.Part
    ): Call<RegisterFestivalRes>
}
