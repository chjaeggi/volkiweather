package com.chjaeggi.volkiweather.weather


import com.chjaeggi.volkiweather.BuildConfig
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WeatherCloudApi {

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
        private const val DATA_PATH = "data/2.5/weather"
        private const val APP_KEY_PARAM = "appid=${BuildConfig.WeatherApiKey}"
        private const val WEATHER_QUERY = "q=volketswil"
        private const val UNITS = "units=metric"

        fun newInstance(): WeatherCloudApi {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(WeatherCloudApi::class.java)
        }
    }

    @GET("$DATA_PATH?$WEATHER_QUERY&$APP_KEY_PARAM&$UNITS")
    fun currentWeather(): Single<WeatherProperties>
}