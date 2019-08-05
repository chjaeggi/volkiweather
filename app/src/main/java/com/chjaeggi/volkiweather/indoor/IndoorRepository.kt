package com.chjaeggi.volkiweather.indoor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.sensirion.android.things.drivers.sht3x.Sht3xSensorDriver
import java.io.IOException

class IndoorRepository(private val context: Context) : IndoorDataSource {

    private lateinit var mSensorManager: SensorManager
    private lateinit var mSensorDriver: Sht3xSensorDriver
    private lateinit var temperatureListener: SensorEventListener
    private lateinit var humidityListener: SensorEventListener

    override fun start(
        temperatureEventListener: SensorEventListener,
        humidityEventListener: SensorEventListener
    ) {
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperatureListener = temperatureEventListener
        humidityListener = humidityEventListener
        mSensorManager.registerDynamicSensorCallback(object :
            SensorManager.DynamicSensorCallback() {
            override fun onDynamicSensorConnected(sensor: Sensor) {
                when {
                    sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE -> mSensorManager.registerListener(
                        temperatureListener, sensor,
                        SensorManager.SENSOR_DELAY_NORMAL
                    )
                    sensor.type == Sensor.TYPE_RELATIVE_HUMIDITY -> mSensorManager.registerListener(
                        humidityListener, sensor,
                        SensorManager.SENSOR_DELAY_NORMAL
                    )
                }
            }
        })

        try {
            mSensorDriver = Sht3xSensorDriver("I2C1")
            mSensorDriver.registerTemperatureSensor()
            mSensorDriver.registerHumiditySensor()
        } catch (e: IOException) {
            // Error configuring sensor
        }
    }

    override fun stop() {
        mSensorManager.unregisterListener(temperatureListener)
        mSensorManager.unregisterListener(humidityListener)
        mSensorDriver.unregisterTemperatureSensor()
        mSensorDriver.unregisterHumiditySensor()
        try {
            mSensorDriver.close()
        } catch (e: IOException) {
            // error closing sensor
        }
    }
}