package com.chjaeggi.volkiweather
import android.app.Application
import com.chjaeggi.volkiweather.di.appModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class VolkiWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}