package com.chjaeggi.volkiweather.weather

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class WeatherRepository(
    private val weatherCloudApi: WeatherCloudApi
) : WeatherDataSource {

    override fun getWeatherData(): Observable<String> {
        return Observable.interval(1, TimeUnit.SECONDS)
            .flatMapSingle {
                weatherCloudApi
                    .currentWeather()
                    .map {
                        it.weather[0].main + "\n" + it.main.temp + "°C / " + it.main.humidity + "%"
                    }
            }
    }

}