package com.example.festo.data.res

class BoothAnalysisRes (
    val booth_id : Int,
    val booth_name : String,
    val booth_image_url : String,
    val menu : List<MenuAnalysis>,
    val daily_sales : List<BoothDailySales>
)

class MenuAnalysis (
    val menu_id : Int,
    val name : String,
    val image_url : String,
    val price : Int,
    val count : Int,
    val amount : Int
)

class BoothDailySales (
    val date : String,
    val count : Int,
    val amount : Int
)