package com.zzq.common.utils

import android.content.Context
import androidx.fragment.app.Fragment

fun Fragment.saveDataToSp(name:String,key: String, value: String) {
    requireContext().getSharedPreferences(name, Context.MODE_PRIVATE).edit().putString(key, value).apply()
}

fun Fragment.getDataFromSp(name: String, key: String) :String?{
    return requireContext().getSharedPreferences(name,Context.MODE_PRIVATE).getString(key,"")
}