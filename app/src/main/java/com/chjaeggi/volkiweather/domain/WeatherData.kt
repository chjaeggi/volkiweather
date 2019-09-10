package com.chjaeggi.volkiweather.domain

/**
 * Immutable model class for weather data in metric units
 */
data class WeatherData(
    val description: String = "",
    val icon: Int = 0,
    val wind: Float = 0.0f,                             // km/h
    val clouds: Float = 0.0f,                           // %
    val temperatureCurrent: Float = 0.0f,               // °C
    val humidity: Float = 0.0f                          // %
)

