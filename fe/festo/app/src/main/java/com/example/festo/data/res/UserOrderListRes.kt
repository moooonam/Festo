package com.example.festo.data.res

import java.sql.Time

class UserOrderListRes (
    val festivalName: String,
    val orderNo: orderNo,
    val boothId: String,
    val imageUrl: String,
    val boothName: String,
    val orderId: Int,
    val time: String,
    val orderStatus: String,
    val productName: String,
    val etcCount: Int
)

class orderNo (
   val number : Int,
)