package com.nowusee.festo.data.req

class MenuRecommendReq (
    val boothId : Int,
    val orderList : List<OrderList>
)

class OrderList (
    val product_id : Int,
    val cnt : Int
)