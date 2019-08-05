package com.chjaeggi.volkiweather.indoor

import android.hardware.SensorEventListener

interface IndoorDataSource {

    fun start(
        temperatureEventListener: SensorEventListener,
        humidityEventListener: SensorEventListener
    )

    fun stop()
}