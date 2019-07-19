package com.chjaeggi.volkiweather.di
import com.chjaeggi.volkiweather.MainViewModel
import com.chjaeggi.volkiweather.weather.WeatherCloudApi
import com.chjaeggi.volkiweather.weather.WeatherDataSource
import com.chjaeggi.volkiweather.weather.WeatherRepository
import com.chjaeggi.volkiweather.util.AppRxSchedulers
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module(override = true) {

    single { AppRxSchedulers() }
    single<WeatherDataSource> {
        WeatherRepository(
            WeatherCloudApi.newInstance()
        )
    }

    viewModel { MainViewModel(get(), get())}
}
