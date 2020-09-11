package com.zzq.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvTitle = findViewById<TextView>(R.id.tv_title)

        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        tvTitle.text = packageInfo.packageName
    }
}
