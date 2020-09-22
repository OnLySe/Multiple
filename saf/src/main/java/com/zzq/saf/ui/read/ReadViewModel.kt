package com.zzq.saf.ui.read

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

class ReadViewModel : ViewModel() {

//    val readFileLiveData = liveData<String> {
//        emit(dealFileData())
//    }


    private suspend fun readSelectedFile(uri: Uri) {
        withContext(Dispatchers.IO) {
            Log.e("tetetetete", "readSelectedFile " + uri.authority)
        }
    }

    suspend inline fun dealFileData(inputStream: InputStream?): String {
        val task = withContext(Dispatchers.IO) {
            async {
                val bufferedInputStream = BufferedInputStream(inputStream!!)
                try {
                    val allData = ArrayList<Byte>()
                    //每次读取4k
                    val buffer = ByteArray(1024 * 4)
                    var bytesRead = 0
                    var tempLength = 0
                    while (bufferedInputStream.read(buffer).also { bytesRead = it } != -1) {
                        for (i in 0 until bytesRead) {
                            val by = buffer[i]
                            allData.add(by)
                        }
                        tempLength += bytesRead
                    }
                    Log.e("dealFileData", "file size: " + allData.size)
                    val fileData = ByteArray(allData.size)
                    for (i in allData.indices) {
                        fileData[i] = allData[i]
                    }
                    bufferedInputStream.close()
                    return@async String(fileData)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    return@async "选择文件错误"
                    //TODO 选择文件错误
                } catch (e: IOException) {
                    e.printStackTrace()
                    //TODO 选择文件错误
                    return@async "选择文件错误"
                } finally {
                    bufferedInputStream.close()
                    inputStream.close()
                }
            }
        }
        return task.await()
    }

}