package com.nowusee.festo.customer_ui.home

import java.io.Serializable

class Booth (
    val logo: Int,
    val name : String,
    val category : String,
    val explanation : String,
    val waitCount : String,
    val waitTime : String,
    val accumulation : String,
): Serializable {}