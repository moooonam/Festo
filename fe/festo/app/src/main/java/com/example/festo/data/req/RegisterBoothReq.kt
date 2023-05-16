package com.example.festo.data.req

import okhttp3.MultipartBody

data class RegisterBoothReq(
    val request : RegiBoothRequest,
    val boothImg : MultipartBody.Part
    // 멀티파트? 로 바꿔야함
)

data class RegiBoothRequest (
    val boothName: String,
    val location: String,
    val description: String,
    val openTime: String?,
    val closeTime: String?,
)
