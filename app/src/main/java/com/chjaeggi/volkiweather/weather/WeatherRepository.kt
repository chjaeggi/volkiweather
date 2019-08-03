package com.chjaeggi.volkiweather.weather

import com.chjaeggi.volkiweather.domain.WeatherData
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class WeatherRepository(
    private val weatherCloudApi: WeatherCloudApi
) : WeatherDataSource {

    override fun getWeatherData(): Observable<WeatherData> {
        return Observable.interval(1, TimeUnit.SECONDS)
            .flatMapSingle {
                weatherCloudApi
                    .currentWeather()
                    .map {
                        WeatherData(
                            description = it.weather[0].main,
                            temperatureCurrent = it.main.temp.toFloat()
                        )
                    }
            }
    }

}