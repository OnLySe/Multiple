package com.zzq.util

import android.app.Application
import android.content.Context
import android.os.Handler
import android.widget.Toast

object ToastUtil {
    private var sToast: Toast? = null
    private val sHandler = Handler()

    /**
     * 可以在Application初始化就赋值
     */
    lateinit var app: Application
    fun showToast(msg: String?) {
        if (null == sToast) {
            sToast = Toast.makeText(app, msg, Toast.LENGTH_SHORT)
        } else {
            sToast!!.setText(msg)
        }
        sToast!!.cancel()
        sHandler.postDelayed({ sToast!!.show() }, 200)
    }

    fun showLongToast(msg: String?) {
        if (null == sToast) {
            sToast = Toast.makeText(app, msg, Toast.LENGTH_LONG)
        } else {
            sToast!!.setText(msg)
        }
        sToast!!.cancel()
        sHandler.postDelayed({ sToast!!.show() }, 200)
    }

    fun showToast(context: Context?, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}