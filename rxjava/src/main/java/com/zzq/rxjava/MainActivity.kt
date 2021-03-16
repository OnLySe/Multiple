package com.zzq.rxjava

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zzq.common.utils.showToast

class MainActivity : AppCompatActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvInfo = findViewById<TextView>(R.id.tv_info)

        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        tvInfo.text = packageInfo.packageName

        findViewById<Button>(R.id.btn_1).setOnClickListener(this)
        findViewById<Button>(R.id.btn_2).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_1 -> {
                startActivity(Intent(this, SimpleActivity::class.java))
            }

            R.id.btn_2 -> {
                showToast("未开始。")
            }
        }
    }
}
