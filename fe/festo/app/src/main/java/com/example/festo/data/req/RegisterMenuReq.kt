package com.example.festo.data.req

data class RegisterMenuReq(
    val request: RegiMenuReq,
    val producImage: String, //멀티파트 바꿔야행
)
data class RegiMenuReq (
    val productName: String,
    val price: Int
    )

