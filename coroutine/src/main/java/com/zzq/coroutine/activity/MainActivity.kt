package com.zzq.coroutine.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zzq.coroutine.R
import com.zzq.coroutine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.itemClickListener1 = View.OnClickListener {
            startActivity(Intent(this@MainActivity, CoroutineInitialActivity::class.java))
        }

        binding.itemClickListener2 = View.OnClickListener {
            startActivity(Intent(this@MainActivity, NetRequestActivity::class.java))
        }

        binding.itemClickListener3 = View.OnClickListener {
            startActivity(Intent(this@MainActivity, WriteActivity::class.java))
        }
    }

}
