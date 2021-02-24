package com.zzq.common.utils

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

object LogUtil {

    /**
     * [AppCompatActivity]与[Fragment]都能直接使用，因为它们的父类都实现了[LifecycleOwner]接口
     */
    fun LifecycleOwner.eLog(msg: String? = null) {
        if (msg == null) {
            return
        }
        Log.e(this.javaClass.simpleName, msg)
    }

    fun LifecycleObserver.eLog(msg: String? = null) {
        if (msg == null) {
            return
        }
        Log.e(this.javaClass.simpleName, msg)
    }

    /**
     * 打印ViewModel消息
     * 但是需要注意：只适合在debug环境使用，release可能会混淆类名
     */
    fun ViewModel.eLog(tag: String? = null, msg: String? = null) {
        e(tag, msg)
    }

    fun ViewModel.eLog(msg: String? = null) {
        if (msg == null) {
            return
        }
        Log.e(this.javaClass.simpleName, msg)
    }

    /**
     * 打印LiveData消息
     * 但是需要注意：只适合在debug环境使用，release可能会混淆类名
     */
    fun <T> LiveData<T>.eLog(tag: String? = null, msg: String? = null) {
        e(tag, msg)
    }

    fun e(tag: String? = null, msg: String? = null) {
        if (msg == null) {
            return
        }
        Log.e(tag ?: this.javaClass.simpleName, msg)
    }
}