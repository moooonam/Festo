package com.nowusee.festo.data.res

data class BoothOrderListRes(
    val orderId: Long,
    val orderNo: OderNumber,
    val orderStatus: String,
    val time: String,
    val firstMenuName: String,
    val etcCount: Int
)

data class OderNumber(
    val number: Long
)
