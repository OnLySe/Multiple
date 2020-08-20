package com.zzq.util

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

object LogUtil {
    fun AppCompatActivity.eLog(msg: String) {
        Log.e(this.javaClass.simpleName, msg)
    }
}