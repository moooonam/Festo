package com.example.festo.data.req

data class OrderReq (
    val boothId: Long,
    val orderInfo: OrderInfo,
    val paymentInfo: PaymentInfo
)

data class OrderInfo (
    val productId: Long,
    val quantity: Int
)

data class PaymentInfo (
    val paymentKey: String,
    val orderId: String,
    val amount: Int
)