package com.chjaeggi.volkiweather.weather

import io.reactivex.Observable


interface WeatherDataSource {
    fun getWeatherData() : Observable<String>
}