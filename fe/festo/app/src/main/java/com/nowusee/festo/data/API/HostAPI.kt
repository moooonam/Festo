package com.nowusee.festo.data.API

import com.nowusee.festo.data.req.RegiFestivalRequest
import com.nowusee.festo.data.res.FestivalAnalysisRes
import com.nowusee.festo.data.res.FestivalCodeRes
import com.nowusee.festo.data.res.MyFestivalRes
import com.nowusee.festo.data.res.RegisterFestivalRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface HostAPI {

    //축제 등록
    @Multipart
    @POST("festivals")
    fun registerFestival(
        @Header("Authorization") token: String,
        @Part("request") request: RegiFestivalRequest,
        @Part festivalImg: MultipartBody.Part
    ): Call<RegisterFestivalRes>

    //이미지 없이 축제등록
    @Multipart
    @POST("festivals")
    fun registerNoImageFestival(
        @Header("Authorization") token: String,
        @Part("request") request: RegiFestivalRequest,
        @Part festivalImg: MultipartBody.Part
    ): Call<RegisterFestivalRes>
    //
    @GET("festivals/manager")
    fun getMyFestival(
        @Header("Authorization") token: String,
    ) :Call<List<MyFestivalRes>>

    //  축제 코드 조회
    @GET("festivals/{festival_id}/invitecode")
    fun getFestivalCode(@Header("Authorization") token: String, @Path("festival_id") festival_id : String): Call<FestivalCodeRes>


    // 축제 매출 분석
    @GET("festivals/{festival_id}/sales")
    fun getFestivalSalesAnalysis(@Header("Authorization") token: String, @Path("festival_id") festival_id: String): Call<FestivalAnalysisRes>

}
