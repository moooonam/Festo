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

    //축제 등록
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjkzNDUxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4Nzc0NTEsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjkzNDUxfQ.SYVxlhNtpJ7dJZILRo4IK-PKejaocbVciEk6Fo6raI4")
    @Multipart
    @POST("festivals")
    fun registerFestival(@Part("request") request: RegiFestivalRequest, @Part festivalImg: MultipartBody.Part): Call<Long>
}
