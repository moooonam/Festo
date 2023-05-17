package com.nowusee.festo.customer_ui.home

import java.io.Serializable

class RecommendMenu (
    val product_id: String,
    val image: String,
    val name : String,
    val price : Int,
): Serializable {}