package com.nowusee.festo.data.res

class FestivalAnalysisRes (
    val daily_sales : List<FestivalDailySales>,
    val booth_data : List<BoothData>,
)
class FestivalDailySales (
    val date : String,
    val count : Int,
    val amount : Int
)

class BoothData (
    val booth_id : Int,
    val booth_name : String,
    val image_url : String,
    val amount : Int,
    val count : Int
)
