package com.chjaeggi.volkiweather

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.lifecycle.MutableLiveData
import com.chjaeggi.volkiweather.indoor.IndoorDataSource
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
    private val data: WeatherDataSource,
    private val indoor: IndoorDataSource
) :
    RxAwareViewModel() {

    private val _dateTime = MutableLiveData<String>()
    val dateTime = _dateTime

    private val _currentWeatherDescription = MutableLiveData<String>()
    val currentWeatherDescription = _currentWeatherDescription

    private val _currentWeatherTemperature = MutableLiveData<String>()
    val currentWeatherTemperature = _currentWeatherTemperature

    private val _currentIndoorTemperature = MutableLiveData<String>()
    val currentIndoorTemperature = _currentIndoorTemperature

    private val _currentIndoorHumidity = MutableLiveData<String>()
    val currentIndoorHumidity = _currentIndoorHumidity

    private var _lowPassTemperature = 0.0f
    private var _lowPassHumidity = 0.0f
    private val _temperatureSmoothing = 40
    private val _humiditySmoothing = 100

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

    fun startIndoorSensing() {
        indoor.start(_temperatureListener, _humidityListener)
    }

    override fun onCleared() {
        indoor.stop()
        super.onCleared()
    }

    // Callback when SensorManager delivers temperature data.
    private val _temperatureListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            _lowPassTemperature += (event.values[0] - _lowPassTemperature) / _temperatureSmoothing
            _currentIndoorTemperature.value = String.format("%.1f °C", _lowPassTemperature)
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Timber.d("current accuracy: $accuracy")
        }
    }

    // Callback when SensorManager delivers humidity data.
    private val _humidityListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            _lowPassHumidity += (event.values[0] - _lowPassHumidity) / _humiditySmoothing
            _currentIndoorHumidity.value = String.format("%.1f %%", _lowPassHumidity)
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Timber.d("current accuracy: $accuracy")
        }
    }
}