package com.example.festo.data.res

import java.sql.Time

class BoothDetailRes (
    val boothId: Long,
    val imageUrl: String,
    val name : String,
    val category : String,
    val boothDescription : String,
    val locationDescription : String,
    val status : String,
    val openTime: String,
    val closeTime: String,
)