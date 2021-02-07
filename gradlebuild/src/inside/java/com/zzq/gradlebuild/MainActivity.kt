package com.zzq.gradlebuild

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zzq.gradlebuild.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)
        dataBinding.tvInfo.text = generateData()

        getWifiInfo()
    }

    private fun generateData(): String {

        val applicationInfo = applicationInfo
        val packageManager = packageManager

        val packageInfo = packageManager.getPackageInfo(packageName, 0)

        return "Main: \n" +
                "packageName: ${applicationInfo.packageName}\n" +
                "versionName: ${packageInfo.versionName}\n" +
                "versionCode: ${packageInfo.versionCode}"
    }

    private fun getWifiInfo() {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiList = wifiManager.scanResults
        wifiList.forEach {
            Log.e("tetetetete", it.toString())
        }

    }
}
