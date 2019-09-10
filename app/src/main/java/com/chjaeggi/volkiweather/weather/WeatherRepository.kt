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
                            icon = weatherToImageResource(it.weather[0].id, it.sys.sunrise, it.sys.sunset)
                        )
                    }
            }
    }

    private fun weatherToImageResource(id: Int, sunrise: Int, sunset: Int): Int {
        val currentTime = System.currentTimeMillis() / 1000
        return when (id) {
            in 200..232 -> R.drawable.ic_thunderstorm
            in 300..310 -> R.drawable.ic_l_rain
            in 311..313 -> R.drawable.ic_m_rain
            in 314..321 -> R.drawable.ic_h_rain
            500 -> R.drawable.ic_l_rain
            501 -> R.drawable.ic_m_rain
            511 -> R.drawable.ic_snow_rain
            in 520..532 -> R.drawable.ic_m_rain
            in 600..622 -> R.drawable.ic_snow
            in 701..781 -> R.drawable.ic_fog
            800 -> clearWeatherIcon(
                (sunrise > currentTime && sunset > currentTime) ||
                        (sunrise < currentTime && sunset < currentTime)
            )
            801 -> lightCloudyWeatherIcon(
                (sunrise > currentTime && sunset > currentTime) ||
                        (sunrise < currentTime && sunset < currentTime)
            )
            802 -> R.drawable.ic_cloudy
            803 -> R.drawable.ic_h_cloudy
            else -> R.drawable.ic_android
        }
    }

    private fun clearWeatherIcon(isNight: Boolean): Int {
        return if (isNight) {
            R.drawable.ic_clear_night
        } else {
            R.drawable.ic_clear_day
        }
    }

    private fun lightCloudyWeatherIcon(isNight: Boolean): Int {
        return if (isNight) {
            R.drawable.ic_l_cloudy_night
        } else {
            R.drawable.ic_l_cloudy_day
        }
    }
}