package com.example.festo.customer_ui.home

import java.io.Serializable

class MyOrderList (
    val image: Int,
    val name : String,
    val price : Int,
    var cnt : Int ,
): Serializable {}