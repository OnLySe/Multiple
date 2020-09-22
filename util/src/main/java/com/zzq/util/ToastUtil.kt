package com.zzq.util

import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

private var sToast: Toast? = null
private val sHandler = Handler()

fun Fragment.showToast(msg: String) {
    if (null == sToast) {
        sToast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
    } else {
        sToast!!.setText(msg)
    }
    sToast!!.cancel()
    sHandler.postDelayed({ sToast!!.show() }, 200)
}

fun Fragment.showLongToast(msg: String) {
    if (null == sToast) {
        sToast = Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG)
    } else {
        sToast!!.setText(msg)
    }
    sToast!!.cancel()
    sHandler.postDelayed({ sToast!!.show() }, 200)
}

fun AppCompatActivity.showToast(msg: String) {
    if (null == sToast) {
        sToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    } else {
        sToast!!.setText(msg)
    }
    sToast!!.cancel()
    sHandler.postDelayed({ sToast!!.show() }, 200)
}

fun AppCompatActivity.showLongToast(msg: String) {
    if (null == sToast) {
        sToast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
    } else {
        sToast!!.setText(msg)
    }
    sToast!!.cancel()
    sHandler.postDelayed({ sToast!!.show() }, 200)
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
