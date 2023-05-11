package com.example.festo.data.API

import com.example.festo.data.req.RegiFestivalRequest
import com.example.festo.data.req.RegisterFestivalReq
import com.example.festo.data.req.TestReq
import com.example.festo.data.res.FestivalCodeRes
import com.example.festo.data.res.RegisterBoothRes
import com.example.festo.data.res.RegisterFestivalRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface HostAPI {

    //축제 등록
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNzIzNzg5LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg5MDc3ODksInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNzIzNzg5fQ.AASSSNLIMLfhVMAkhwRhZswzoSoYdqrOhqZYTGt74aA")
    @Multipart
    @POST("festivals")
    fun registerFestival(
        @Part("request") request: RegiFestivalRequest,
        @Part festivalImg: MultipartBody.Part
    ): Call<RegisterFestivalRes>

    //  축제 코드 조회
    @GET("/festivals/{festival_id}/invitecode")
    fun getFestivalCode(@Header("Authorization") token: String, @Path("festival_id") festival_id : String): Call<FestivalCodeRes>

}
