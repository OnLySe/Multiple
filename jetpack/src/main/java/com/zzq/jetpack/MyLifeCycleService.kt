package com.zzq.jetpack

import android.util.Log
import androidx.lifecycle.LifecycleService
import com.zzq.jetpack.module.LifeCycleModel

class MyLifeCycleService : LifecycleService() {

    private lateinit var lifecycleModel: LifeCycleModel

    init {
        Log.d("LifeCycleModel","MyLifeCycleService init")
    }

    override fun onCreate() {
        super.onCreate()
        lifecycleModel = LifeCycleModel(this)
        lifecycle.addObserver(lifecycleModel)
        Log.d("LifeCycleModel","MyLifeCycleService onCreate")
    }
}