package com.chjaeggi.volkiweather.domain

/**
 * Immutable model class for weather data in metric units
 */
data class WeatherData(
    val id: Int = 0,
    val description: String = "",
    val icon: Int = 0,
    val wind: Float = 0.0f,                             // km/h
    val clouds: Float = 0.0f,                           // %
    val temperatureCurrent: Float = 0.0f,               // °C
    val temperatureHigh: Float = 0.0f,                  // °C
    val temperatureLow: Float = 0.0f,                   // °C
    val humidity: Float = 0.0f,                         // %
    val sunrise: Int = 0,                               // timestamp
    val sunset: Int = 0,                                // timestamp
    val idTomorrow: Int = 0,
    val temperatureTomorrow: Float = 0.0f,              // °C
    val humidityTomorrow: Float = 0.0f                  // %
)

