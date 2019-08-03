package com.chjaeggi.volkiweather

import androidx.lifecycle.MutableLiveData
import com.chjaeggi.volkiweather.util.AppRxSchedulers
import com.chjaeggi.volkiweather.util.RxAwareViewModel
import com.chjaeggi.volkiweather.util.plusAssign
import com.chjaeggi.volkiweather.weather.WeatherDataSource
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(
    private val schedulers: AppRxSchedulers,
    private val data: WeatherDataSource
) :
    RxAwareViewModel() {

    private val _dateTime = MutableLiveData<String>()
    val dateTime = _dateTime

    private val _currentWeatherDescription = MutableLiveData<String>()
    val currentWeatherDescription = _currentWeatherDescription

    private val _currentWeatherTemperature = MutableLiveData<String>()
    val currentWeatherTemperature = _currentWeatherTemperature

    fun requestWeatherData() {
        disposables += data
            .getWeatherData()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .subscribeBy(
                onNext = {
                    dateTime.value = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy\nHH:mm"))
                    currentWeatherDescription.value = it.description
                    currentWeatherTemperature.value = "${it.temperatureCurrent} °C"
                },
                onError = {
                    Timber.d(it)
                }
            )
    }
}