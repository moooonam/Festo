package com.nowusee.festo.data.res

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
