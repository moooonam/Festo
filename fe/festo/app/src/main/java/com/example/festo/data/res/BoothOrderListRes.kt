package com.example.festo.data.res

import java.time.LocalDateTime

data class BoothOrderListRes(
    val orderNo: Long,
    val status: String,
    val time: LocalDateTime,
    val firstProductName: String,
    val etcCount: Int
)
