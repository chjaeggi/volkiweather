package com.chjaeggi.volkiweather.di
import com.chjaeggi.volkiweather.MainViewModel
import com.chjaeggi.volkiweather.indoor.IndoorDataSource
import com.chjaeggi.volkiweather.indoor.IndoorRepository
import com.chjaeggi.volkiweather.weather.WeatherCloudApi
import com.chjaeggi.volkiweather.weather.WeatherDataSource
import com.chjaeggi.volkiweather.weather.WeatherRepository
import com.chjaeggi.volkiweather.util.AppRxSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module(override = true) {

    single { AppRxSchedulers() }
    single<WeatherDataSource> {
        WeatherRepository(
            WeatherCloudApi.newInstance()
        )
    }
    single<IndoorDataSource> {
        IndoorRepository(androidContext())
    }

    viewModel { MainViewModel(get(), get(), get())}
}
