package com.zzq.jetpack.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.zzq.common.utils.LogUtil.eLog

class ApplicationObserver : LifecycleObserver {

    private val TAG = "ApplicationObserver"

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        eLog("lifecycle onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        eLog("lifecycle onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        eLog("lifecycle onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        eLog("lifecycle onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        eLog("lifecycle onStop")
    }

    /**
     * TODO 应用程序退出时，没有执行这里
     * 永远不会调用，系统不会分发调用ON_DESTROY事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        eLog("lifecycle onDestroy")
    }

}