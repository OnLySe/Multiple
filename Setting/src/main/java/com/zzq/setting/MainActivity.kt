package com.zzq.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zzq.setting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
    }
}
