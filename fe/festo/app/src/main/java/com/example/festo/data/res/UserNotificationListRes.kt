package com.example.festo.data.res

import java.util.Date

data class UserNotificationListRes(
    val dateTime: Date, //import 유틸인지 맞는지 확인 해야해
    val boothName: String,
    val content: String
)
