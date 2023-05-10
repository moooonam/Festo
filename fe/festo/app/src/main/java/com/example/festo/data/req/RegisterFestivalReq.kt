package com.example.festo.data.req

import okhttp3.MultipartBody

data class RegisterFestivalReq(
    val request : RegiFestivalRequest,
    val festivalImg : MultipartBody.Part
    // 멀티파트? 로 바꿔야함
)

data class RegiFestivalRequest (
    val festivalName: String,
    val location: String,
    val description: String,
    val startDate: String?,
    val endDate: String?,
 )
