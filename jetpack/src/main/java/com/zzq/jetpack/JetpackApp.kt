package com.zzq.jetpack

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.zzq.jetpack.module.ApplicationObserver

class JetpackApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        //监听应用程序的生命周期
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationObserver())
    }

    companion object {
        private lateinit var instance:JetpackApp
        fun getInstance():JetpackApp {
            return instance
        }
    }
}