package com.chjaeggi.volkiweather

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chjaeggi.volkiweather.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModel()
    private val intentFilter = IntentFilter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = viewModel

        binding.lifecycleOwner = this

        if (wifiAvailable()) {
            viewModel.requestWeatherData()
        } else {
            intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
            registerReceiver(broadCastReceiver, intentFilter)
        }

        viewModel.startIndoorSensing()
    }

    private fun wifiAvailable(): Boolean {
        return (getSystemService(Context.WIFI_SERVICE) as WifiManager).connectionInfo.networkId != -1
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val info = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
            if (info != null && info.isConnected) {
                viewModel.requestWeatherData()
            }
        }
    }
}