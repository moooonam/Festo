package com.nowusee.festo.data.req

data class LoginReq(
    val authId: Long?,
    val nickname: String?,
    val profileImageUrl:String?,
    val fcmDeviceToken:String?
)
