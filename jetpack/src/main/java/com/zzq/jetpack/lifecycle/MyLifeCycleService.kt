package com.zzq.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.LifecycleService

class MyLifeCycleService : LifecycleService() {

    private lateinit var lifecycleModel: LifecycleModel

    init {
        Log.d("LifeCycleModel","MyLifeCycleService init")
    }

    override fun onCreate() {
        super.onCreate()
        lifecycleModel = LifecycleModel(this)
        lifecycle.addObserver(lifecycleModel)
        Log.d("LifeCycleModel","MyLifeCycleService onCreate")
    }
}