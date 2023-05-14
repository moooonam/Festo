package com.example.festo.data.res

import java.time.LocalDateTime

class BoothOrderListCompleteRes (
    val orderId: Long,
    val orderNo: CompleteOderNumber,
    val orderStatus: String,
    val time: String,
    val firstMenuName: String,
    val etcCount: Int
    )
data class CompleteOderNumber(
    val number: Long
)