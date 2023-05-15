package com.example.festo.data.res

data class UserNotificationListRes(
    val notificationId: Long,
    val content: String,
    val festivalId: Long,
    val festivalName: String,
    val boothId: Long,
    val boothName: String,
    val time: String,
)
