package com.example.festo.data.res

import java.sql.Array

data class BoothOrderDetailRes(
    val orderNo:Int,
    val time:String,
    val totalPrice: Int,
    val menus : List<menu>,
)

data class menu (
    val menuName : String,
    val quantity : Int
    )
