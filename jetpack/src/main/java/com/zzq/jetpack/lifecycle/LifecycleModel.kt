package com.zzq.jetpack.lifecycle

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

open class LifecycleModel : LifecycleObserver {

    companion object {
        private val defaultName = "LifecycleModel"
    }

    protected var owner: LifecycleOwner

    protected var specialName: String = defaultName

    constructor(activity: AppCompatActivity, name: String = defaultName) {
        owner = activity
        specialName = name
    }

    constructor(fragment: Fragment, name: String = defaultName) {
        owner = fragment
        specialName = name
    }

    constructor(service: LifecycleService, name: String = defaultName) {
        owner = service
        specialName = name
    }

    init {
        Log.d(specialName, "对象创建！")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        Log.d(specialName, "create $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Log.d(specialName, "start $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        Log.d(specialName, "resume $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        Log.d(specialName, "pause $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Log.d(specialName, "stop $owner")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        Log.d(specialName, "destroy $owner")
    }

    fun unused() {
        Log.d(specialName, "无用方法，只用于区分普通方法、构造方法、带有注解的方法")
    }
}