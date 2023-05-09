package com.example.festo.data.req

data class RegisterBoothReq(
    val request : RegiBoothRequest,
    val boothimg : String
    // 멀티파트? 로 바꿔야함
)

data class RegiBoothRequest (
    val BoothName: String,
    val location: String,
    val description: String,
    val openTime: String?,
    val closeTime: String?,
)
