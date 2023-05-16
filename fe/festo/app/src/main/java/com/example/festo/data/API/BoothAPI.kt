package com.example.festo.data.API

import com.example.festo.data.req.BoothStatusReq
import com.example.festo.data.req.ChangeOrderStateReq
import com.example.festo.data.req.RegiBoothRequest
import com.example.festo.data.req.RegiMenuReq
import com.example.festo.data.res.BoothAnalysisRes
import com.example.festo.data.res.BoothOrderDetailRes
import com.example.festo.data.res.BoothOrderListCompleteRes
import com.example.festo.data.res.BoothOrderListRes
import com.example.festo.data.res.FestivalIdRes
import com.example.festo.data.res.RegisterBoothRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface BoothAPI {

    // 부스 등록하기
    @Multipart
    @POST("festivals/{festival_id}/booths")
    fun registerBooth(
        @Header("Authorization") token: String,
        @Path("festival_id") festival_id: Long,
        @Part("request") request: RegiBoothRequest,
        @Part boothImg: MultipartBody.Part
    ): Call<RegisterBoothRes>
    // 이미지 없이 부스 등록하기
    @Multipart
    @POST("festivals/{festival_id}/booths")
    fun registerNoImageBooth(
        @Header("Authorization") token: String,
        @Path("festival_id") festival_id: Long,
        @Part("request") request: RegiBoothRequest,
        @Part boothImg: MultipartBody.Part
    ): Call<RegisterBoothRes>

    // 메뉴 등록하기
    @Multipart
    @POST("booths/{booth_id}/menu")
    fun registerMenu(
        @Header("Authorization") token: String,
        @Path("booth_id") booth_id: Long,
        @Part("request") request: RegiMenuReq,
        @Part productImage: MultipartBody.Part
    ): Call<Long>

    //
    @Multipart
    @POST("booths/{booth_id}/menu")
    fun registerNoImageMenu(
        @Header("Authorization") token: String,
        @Path("booth_id") booth_id: Long,
        @Part("request") request: RegiMenuReq,
        @Part productImage: MultipartBody.Part
    ): Call<Long>

    // 부스 신규,준비중 주문내역 불러오기
    @GET("booths/{booth_id}/orders?completed=false")
    fun getBoothOrderList(
        @Header("Authorization") token: String,
        @Path("booth_id") booth_id: String
    ): Call<List<BoothOrderListRes>>

    // 부스 완료된 주문내역 불러오기

    @GET("booths/{booth_id}/orders?completed=true")
    fun getBoothOrderListComplete(
        @Header("Authorization") token: String,
        @Path("booth_id") booth_id: String
    ): Call<List<BoothOrderListCompleteRes>>

    // 주문상세내역 조회
    @GET("orders/{order_id}")
    fun getBoothOrderDetail(
        @Header("Authorization") token: String,
        @Path("order_id") order_id: String
    ) : Call<BoothOrderDetailRes>

    // 주문상태변경
    @PATCH("orders/{order_id}/status")
    fun changeOrderStatus(
        @Header("Authorization") token: String,
        @Path("order_id") order_id: String,
        @Body data: ChangeOrderStateReq
    ) : Call<Long>

    // 부스 등록 전 축제 코드 입력
    @GET("festivals/invitation")
    fun getFestivalCodeCheck(@Header("Authorization") token: String, @Query("inviteCode") code: String): Call<FestivalIdRes>

    // 부스 영업 상태 변경
     @PATCH("booths/{booth_id}/status")
    fun changeBoothStatus(@Header("Authorization") token: String, @Path("booth_id") booth_id: String, @Body data: BoothStatusReq): Call<Void>


    // 부스 매출 분석
    @GET("data/booths/{booth_id}/sales")
    fun getBoothSalesAnalysis(@Header("Authorization") token: String, @Query("booth_id") booth_id: String): Call<BoothAnalysisRes>
}