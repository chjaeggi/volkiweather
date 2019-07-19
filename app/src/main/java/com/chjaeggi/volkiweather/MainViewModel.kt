package com.chjaeggi.volkiweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chjaeggi.volkiweather.util.AppRxSchedulers
import com.chjaeggi.volkiweather.util.RxAwareViewModel
import com.chjaeggi.volkiweather.util.plusAssign
import com.chjaeggi.volkiweather.weather.WeatherDataSource
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.text.DateFormat
import java.util.*

class MainViewModel(
    private val schedulers: AppRxSchedulers,
    private val data: WeatherDataSource
) :
    RxAwareViewModel() {

    private val _dateTime = MutableLiveData<String>()
    val dateTime = _dateTime

    fun requestWeatherData() {
        disposables += data
            .getWeatherData()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .subscribeBy(
                onNext = {
                    dateTime.value = "${DateFormat.getDateTimeInstance().format(Date())}\n\n$it"
                },
                onError = {
                    Timber.d(it)
                }
            )
    }
}