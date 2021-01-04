package com.example.xjw2018110454_10.weather

import com.example.xjw2018110454_10.weather.CityInfo
import com.example.xjw2018110454_10.weather.Data

data class Weather(
    val cityInfo: CityInfo,
    val `data`: Data,
    val date: String,
    val message: String,
    val status: Int,
    val time: String
)