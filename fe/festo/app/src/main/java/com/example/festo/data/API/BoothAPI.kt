package com.example.festo.data.API

import com.example.festo.data.req.RegiBoothRequest
import com.example.festo.data.res.BoothOrderListCompleteRes
import com.example.festo.data.res.BoothOrderListRes
import com.example.festo.data.res.FestivalIdRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface BoothAPI {

    // 부스 등록하기
    @Multipart
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjkzNDUxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4Nzc0NTEsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjkzNDUxfQ.SYVxlhNtpJ7dJZILRo4IK-PKejaocbVciEk6Fo6raI4")
    @POST("festivals/{festival_id}/booths")
    fun registerBooth(
        @Path("festival_id") festival_id: String,
        @Part("request") request: RegiBoothRequest,
        @Part boothImg: MultipartBody.Part
    ): Call<Long>

    // 부스 신규,준비중 주문내역 불러오기
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjkzNDUxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4Nzc0NTEsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjkzNDUxfQ.SYVxlhNtpJ7dJZILRo4IK-PKejaocbVciEk6Fo6raI4")
    @GET("booths/{booth_id}/orders?completed=false")
    fun getBoothOrderList(@Path("booth_id") booth_id: String): Call<List<BoothOrderListRes>>

    // 부스 완료된 주문내역 불러오기
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjkzNDUxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4Nzc0NTEsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjkzNDUxfQ.SYVxlhNtpJ7dJZILRo4IK-PKejaocbVciEk6Fo6raI4")
    @GET("booths/{booth_id}/orders?completed=true")
    fun getBoothOrderListComplete(@Path("booth_id") booth_id: String): Call<List<BoothOrderListCompleteRes>>

    // 부스 등록 전 축제 코드 입력
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNjk0MjAxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4NzgyMDEsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNjk0MjAxfQ.AeidYEQ6RWsFYvzh6z1l990YGjAFHCkfiKV85UU2D7E")
    @GET("festivals/invitation")
    fun getFestivalCodeCheck(@Query("inviteCode") code: String): Call<FestivalIdRes>

}