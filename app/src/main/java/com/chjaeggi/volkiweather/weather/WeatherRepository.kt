package com.chjaeggi.volkiweather.weather

import com.chjaeggi.volkiweather.R
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
                            description = it.weather[0].description,
                            temperatureCurrent = it.main.temp.toFloat(),
                            wind = it.wind.speed.toFloat(),
                            humidity = it.main.humidity.toFloat(),
                            clouds = it.clouds.all.toFloat(),
                            icon = convertWeatherIdToImgResource(it.id)
                        )
                    }
            }
    }

    private fun convertWeatherIdToImgResource(id: Int): Int {
        return when (id) {
            800 -> R.drawable.ic_clear_day
            else -> R.drawable.ic_clear_night
        }
    }

}