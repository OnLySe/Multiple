package com.zzq.util

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

object LogUtil {
    fun AppCompatActivity.eLog(msg: String) {
        Log.e(this.javaClass.simpleName, msg)
    }

    /**
     * 打印ViewModel消息
     * 但是需要注意：只适合在debug环境使用，release可能会混淆类名
     */
    fun ViewModel.eLog(msg: String) {
        Log.e(this.javaClass.simpleName, msg)
    }

    /**
     * 打印LiveData消息
     * 但是需要注意：只适合在debug环境使用，release可能会混淆类名
     */
    fun <T> LiveData<T>.eLog(msg: String) {
        Log.e(this.javaClass.simpleName, msg)
    }
}