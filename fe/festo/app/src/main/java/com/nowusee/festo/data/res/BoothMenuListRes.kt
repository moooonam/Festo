package com.nowusee.festo.data.res

class BoothMenuListRes (
    val productId: String,
    val imageUrl: String,
    val name: String,
    val price: Int,
    var cnt: Int = 0,
    var check: Boolean = false
)