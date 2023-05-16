package com.example.festo.data.req

class MenuRecommendReq (
    val booth_id : Int,
    val orderList : List<OrderList>
)

class OrderList (
    val product_id : Int,
    val cnt : Int
)