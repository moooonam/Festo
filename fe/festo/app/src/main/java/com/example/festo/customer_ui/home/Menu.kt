package com.example.festo.customer_ui.home

import java.io.Serializable

class Menu (
    val image: Int,
    val name : String,
    val price : Int,
    var check : Boolean = false,
    var cnt : Int = 0,
): Serializable {}