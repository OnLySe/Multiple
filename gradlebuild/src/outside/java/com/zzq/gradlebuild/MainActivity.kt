package com.zzq.gradlebuild

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zzq.gradlebuild.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)
        dataBinding.tvInfo.text = generateData()
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
}
