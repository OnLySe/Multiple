package com.zzq.view

import android.app.Application

class ViewApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: ViewApp
            private set
    }
}