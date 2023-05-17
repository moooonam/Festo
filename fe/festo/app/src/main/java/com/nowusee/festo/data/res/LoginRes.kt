package com.nowusee.festo.data.res

data class LoginRes(
    val accessToken : String,
    val refreshToken : String,
    val memberId : String
)
