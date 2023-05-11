package com.example.festo.data.req

import okhttp3.MultipartBody

data class RegisterMenuReq(
    val request: RegiMenuReq,
    val productImage: MultipartBody.Part, //멀티파트 바꿔야행
)
data class RegiMenuReq (
    val productName: String,
    val price: Int
    )

