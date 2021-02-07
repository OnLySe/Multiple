package com.zzq.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zzq.common.utils.showToast
import com.zzq.setting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var wifiManager:WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main
        )

        binding.clickListener1 = object : OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS)
                startActivity(intent)
            }
        }

        binding.clickListener2 = object : OnClickListener {
            override fun onClick(v: View?) {
                val packageURI: Uri = Uri.parse("package:" + "com.tencent.wework")
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
                startActivity(intent)
            }

        }

        wifiFunction()

    }

    private fun wifiFunction() {
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            showToast("wifi已关闭")
            return
        }
        if(!wifiManager.startScan()){
            showToast("wifi扫描启动失败！")
            return
        }
        val wifiScanResult = wifiManager.scanResults
        wifiScanResult.forEach {
            Log.e("tetetetete", "wifi scan: ${it.toString()}")
        }
    }
}
