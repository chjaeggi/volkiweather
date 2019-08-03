package com.chjaeggi.volkiweather.weather

import com.chjaeggi.volkiweather.domain.WeatherData
import io.reactivex.Observable


interface WeatherDataSource {
    fun getWeatherData() : Observable<WeatherData>
}