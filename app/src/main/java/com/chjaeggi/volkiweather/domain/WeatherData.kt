package com.chjaeggi.volkiweather.domain

/**
 * Immutable model class for weather data
 */
data class WeatherData(
    val id: Int = 0,
    val description: String = "",
    val wind: Float = 0.0f,
    val temperatureCurrent: Float = 0.0f,
    val temperatureHigh: Float = 0.0f,
    val temperatureLow: Float = 0.0f,
    val humidity: Float = 0.0f,
    val icon: String = "",
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val idTomorrow: Int = 0,
    val temperatureTomorrow: Float = 0.0f,
    val humidityTomorrow: Float = 0.0f
)