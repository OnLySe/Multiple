package com.zzq.saf.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

object WriteUtil {

    suspend fun writeDocument(context: Context, fileName: String, content: String) {
        val filesDir: File = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        if (!filesDir.exists()) {
            filesDir.mkdirs()
        }
        val file = File(filesDir, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }

        withContext(Dispatchers.IO) {

            try {

                val fileOutputStream = FileOutputStream(file)
                val os: OutputStream = BufferedOutputStream(fileOutputStream)
                val data = content.toByteArray()
                os.write(data)
                os.flush()
                os.close()
                Log.e("tetetetete", "File Path= " + file.toString())
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }
}