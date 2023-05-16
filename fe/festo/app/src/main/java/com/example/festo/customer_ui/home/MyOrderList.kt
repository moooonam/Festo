package com.example.festo.customer_ui.home

import java.io.Serializable

class MyOrderList(
    val product_id: String,
    val image: String,
    val name: String,
    val price: Int,
    var cnt: Int,
): Serializable {}