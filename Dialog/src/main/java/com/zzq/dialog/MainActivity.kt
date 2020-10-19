package com.zzq.dialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zzq.common.interfaces.ClickProxy
import com.zzq.dialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        initListener()
    }

    private fun initListener() {
        binding.singleChoiceListener = ClickProxy {
            SingleChoiceDialogFragment().show(supportFragmentManager, "first")
        }
    }
}
