package com.zzq.common.utils

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

object LogUtil {
    fun AppCompatActivity.eLog( msg: String? = null) {
        if (msg == null) {
            return
        }
        Log.e( this.javaClass.simpleName, msg)
    }

    /**
     * 打印ViewModel消息
     * 但是需要注意：只适合在debug环境使用，release可能会混淆类名
     */
    fun ViewModel.eLog(tag: String? = null, msg: String? = null) {
        if (msg == null) {
            return
        }
        Log.e(tag?:this.javaClass.simpleName, msg)
    }
    fun ViewModel.eLog( msg: String? = null) {
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
        if (msg == null) {
            return
        }
        Log.e(tag?:this.javaClass.simpleName, msg)
    }
}