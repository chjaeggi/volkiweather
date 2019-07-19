package com.chjaeggi.volkiweather.weather

import androidx.annotation.Keep

@Keep
data class WeatherProperties(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

@Keep
data class Clouds(
    val all: Int
)

@Keep
data class Coord(
    val lon: Double,
    val lat: Double
)

@Keep
data class Main(
    val temp: Double,
    val pressure: Double,
    val humidity: Double,
    val temp_min: Double,
    val temp_max: Double
)

@Keep
data class Sys(
    val type: Int,
    val id: Int,
    val message: Double,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)

@Keep
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Keep
data class Wind(
    val speed: Double,
    val deg: Double
)