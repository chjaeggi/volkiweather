package com.chjaeggi.volkiweather.domain

/**
 * Immutable model class for weather data
 */
data class WeatherData(
    val id: Int,
    val description: String,
    val wind: Float,
    val temperatureHigh: Float,
    val temperatureLow: Float,
    val humidity: Float,
    val icon: String,
    val sunrise: Int,
    val sunset: Int,
    val idTomorrow: Int,
    val temperatureTomorrow: Float,
    val humidityTomorrow: Float
)