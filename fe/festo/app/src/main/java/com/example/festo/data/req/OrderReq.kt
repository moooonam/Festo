package com.example.festo.data.req

data class OrderReq (
    val boothId: Long,
    val orderMenus: List<OrderInfo>,
    val paymentInfo: PaymentInfo
)

data class OrderInfo(
    val menuId: Long,
    val quantity: Int
)

data class PaymentInfo(
    val paymentKey: String?,
    val orderId: String?,
    val amount: Int
)