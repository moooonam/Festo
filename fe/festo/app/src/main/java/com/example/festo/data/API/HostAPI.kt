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
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface HostAPI {

    //축제 등록
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNzA1ODgyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4ODk4ODIsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNzA1ODgyfQ.5PtXj3w0RR06cq_Rz9KAqlm3RlWwVihAl4kuXMDWZbQ")
    @Multipart
    @POST("festivals")
    fun registerFestival(
        @Part("request") request: RegiFestivalRequest,
        @Part festivalImg: MultipartBody.Part
    ): Call<RegisterFestivalRes>
}
