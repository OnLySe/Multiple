package com.zzq.jetpack.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zzq.jetpack.R
import com.zzq.jetpack.module.LifeCycleModel

class TestLifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lifecycle)

        val lifecycleModel = LifeCycleModel(this)
        lifecycle.addObserver(lifecycleModel)
    }
}