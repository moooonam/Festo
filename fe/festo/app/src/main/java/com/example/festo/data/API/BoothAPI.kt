package com.example.festo.data.API

import com.example.festo.data.req.BoothStatusReq
import com.example.festo.data.req.RegiBoothRequest
import com.example.festo.data.res.BoothOrderListCompleteRes
import com.example.festo.data.res.BoothOrderListRes
import com.example.festo.data.res.FestivalIdRes
import com.example.festo.data.res.RegisterBoothRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface BoothAPI {

    // 부스 등록하기
    @Multipart
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNzA1ODgyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4ODk4ODIsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNzA1ODgyfQ.5PtXj3w0RR06cq_Rz9KAqlm3RlWwVihAl4kuXMDWZbQ")
    @POST("festivals/{festival_id}/booths")
    fun registerBooth(
        @Path("festival_id") festival_id: String,
        @Part("request") request: RegiBoothRequest,
        @Part boothImg: MultipartBody.Part
    ): Call<RegisterBoothRes>

    // 부스 신규,준비중 주문내역 불러오기
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNzA1ODgyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4ODk4ODIsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNzA1ODgyfQ.5PtXj3w0RR06cq_Rz9KAqlm3RlWwVihAl4kuXMDWZbQ")
    @GET("booths/{booth_id}/orders?completed=false")
    fun getBoothOrderList(@Path("booth_id") booth_id: String): Call<List<BoothOrderListRes>>

    // 부스 완료된 주문내역 불러오기
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNzA1ODgyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg4ODk4ODIsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNzA1ODgyfQ.5PtXj3w0RR06cq_Rz9KAqlm3RlWwVihAl4kuXMDWZbQ")
    @GET("booths/{booth_id}/orders?completed=true")
    fun getBoothOrderListComplete(@Path("booth_id") booth_id: String): Call<List<BoothOrderListCompleteRes>>

    // 부스 등록 전 축제 코드 입력
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNzIzODM1LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg5MDc4MzUsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNzIzODM1fQ.TtSFsz7ScldLe5Ny1WhDX8oxs_L9Dz12BQ0d4_6AePo")
    @GET("festivals/invitation")
    fun getFestivalCodeCheck(@Query("inviteCode") code: String): Call<FestivalIdRes>

    // 부스 영업 상태 변경
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNzIzODM1LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg5MDc4MzUsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNzIzODM1fQ.TtSFsz7ScldLe5Ny1WhDX8oxs_L9Dz12BQ0d4_6AePo")
    @PATCH("/booths/{booth_id}/status")
    fun changeBoothStatus(@Path("booth_id") booth_id: String, @Body data: BoothStatusReq): Call<Void>
}