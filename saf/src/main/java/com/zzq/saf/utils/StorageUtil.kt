package com.zzq.saf.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.io.File

object StorageUtil {

    fun Fragment.printStorageInfo() {
        Log.e("tetetetete", obtainStorageInfo(requireContext(),"Fragment").toString())
    }

    fun AppCompatActivity.println() {

    }

    private fun obtainStorageInfo(context:Context,fileName:String):StringBuilder {
        val stringBuilder = StringBuilder()
        var filesDir: File = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        if (!filesDir.exists()) {
            filesDir.mkdirs()
        }
        stringBuilder.append("getExternalFilesDir DIRECTORY_DOCUMENTS ${filesDir.absoluteFile}\n")
        filesDir = context.externalCacheDir!!
        if (!filesDir.exists()) {
            filesDir.mkdirs()
        }
        stringBuilder.append("getExternalCacheDir ${filesDir.absoluteFile}\n")

        return stringBuilder

    }
}