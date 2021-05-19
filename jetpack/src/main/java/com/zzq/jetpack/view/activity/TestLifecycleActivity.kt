package com.zzq.jetpack.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zzq.jetpack.R
import com.zzq.jetpack.lifecycle.ActivityLifecycleModel
import com.zzq.jetpack.lifecycle.LifecycleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestLifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lifecycle)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000)
//            lifecycle.addObserver(LifecycleModel(this@TestLifecycleActivity, "createDelay1000LM"))
            lifecycle.addObserver(ActivityLifecycleModel(this@TestLifecycleActivity))
        }
    }
}