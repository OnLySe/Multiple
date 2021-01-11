package com.zzq.jetpack.module

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

class LifeCycleModel : LifecycleObserver {

    private var owner: LifecycleOwner

    constructor(activity: AppCompatActivity) {
        owner = activity
    }

    constructor(fragment: Fragment) {
        owner = fragment
    }

    constructor(service: LifecycleService) {
        owner = service
    }

    init {
        Log.d("LifeCycleModel", "对象创建！")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        Log.d("LifeCycleModel", "create $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Log.d("LifeCycleModel", "start $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        Log.d("LifeCycleModel", "resume $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        Log.d("LifeCycleModel", "pause $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Log.d("LifeCycleModel", "stop $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        Log.d("LifeCycleModel", "destroy $owner")
    }
}