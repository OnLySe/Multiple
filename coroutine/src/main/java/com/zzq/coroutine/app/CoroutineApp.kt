package com.zzq.coroutine.app

import android.app.Application
import com.zzq.coroutine.net.WanApi

class CoroutineApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        private lateinit var instance: CoroutineApp
        private lateinit var wanApi: WanApi

        fun getInstance(): CoroutineApp {
            return instance
        }
    }
}