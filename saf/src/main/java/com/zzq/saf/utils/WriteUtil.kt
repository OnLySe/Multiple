package com.zzq.saf.utils

import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

object WriteUtil {

    /**
     * 检查文件。传入文件夹和文件名，可检查文件的相关属性，如是否存在，以及传入的文件夹路径本身是否就已经是完整的文件
     * 路径，如果是的话就直接抛出异常。
     *
     * 正确的参数应该是“文件夹路径+文件名”，通过传入的两个参数组合成为一个正确的路径，可以直接访问（读取和写入）
     * @param foldName 文件夹路径
     * @param fileName 文件名，
     */
    @Deprecated("会把文件名当成文件夹名字创建，导致找不到文件FileNotFoundException", replaceWith = ReplaceWith(expression = "checkFile(foldFile,fileName)"))
    fun checkFile(foldName: String, fileName: String): File {

        val folderFile = File(foldName)
        if (!folderFile.exists()) {
            folderFile.mkdirs()
        }
        if (folderFile.isFile) {
            throw IllegalArgumentException("foldName为文件路径，要求应传入指定文件夹路径")
        }
        val filesDir = File(folderFile, fileName)
        if (!filesDir.exists()) {
            filesDir.createNewFile()
        }
        return filesDir
    }

    fun checkFile(foldFile: File, fileName: String): File {

        if (!foldFile.exists()) {
            foldFile.mkdirs()
        }
        val file = File(foldFile, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    /**
     * 外部存储的私有目录下
     */
    suspend fun textWritePrivateDirectory(context: Context, fileName: String, content: String): File {
        Log.e("tetetetete", "textWritePrivateDirectory content：$content")
        val filesDir: File = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
//        val file = checkFile(filesDir.absolutePath, fileName)
        val file = checkFile(filesDir, fileName)
        writeData(file, content)
        return file
    }

    /**
     * 写入到外部存储的根目录下
     */
    suspend fun textWriteRootDirectory(context: Context, uri: DocumentFile, content: String) {
        val outputStream = context.contentResolver.openOutputStream(uri.uri)!!
        writeData(outputStream, content)
    }

    /**
     * 写入到外部存储的公共目录下
     */
    suspend fun textWritePublicDirectory(context: Context, fileName: String, content: String) {

    }

    private suspend fun writeData(file: File, content: String): File {
        withContext(Dispatchers.IO) {
            try {
                //在原文件内容基础上增加新的内容，而不是直接覆盖原有内容
                val writer = BufferedWriter(FileWriter(file, true))
//                val writer = file.bufferedWriter()
                writer.write(content)
                writer.flush()
                writer.close()
                Log.e("tetetetete", "writeData File Path= " + file.absolutePath)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Log.e("tetetetete", "writeData FileNotFoundException!! ${e.message}")
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("tetetetete", "writeData IOException!! ${e.message}")
            }
        }
        return file
    }

    private suspend fun writeData(outputStream: OutputStream, content: String) {
        withContext(Dispatchers.IO) {
            try {
                //在原文件内容基础上增加新的内容，而不是直接覆盖原有内容
                val writer = BufferedOutputStream(outputStream)
//                val writer = file.bufferedWriter()
                writer.write(content.toByteArray())
                writer.flush()
                writer.close()
                Log.e("tetetetete", "writeData File Path= " /*+ file.absolutePath*/)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Log.e("tetetetete", "writeData FileNotFoundException!! ${e.message}")
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("tetetetete", "writeData IOException!! ${e.message}")
            }
        }
//        return file
    }

    private suspend fun writeData(writer: BufferedWriter, content: String) {
        withContext(Dispatchers.IO) {
            try {
                //在原文件内容基础上增加新的内容，而不是直接覆盖原有内容
//                val writer = BufferedWriter(FileWriter(file, true))
//                val writer = file.bufferedWriter()
                writer.write(content)
                writer.flush()
                writer.close()
                Log.e("tetetetete", "writeData File Path= "/* + file.absolutePath*/)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Log.e("tetetetete", "writeData FileNotFoundException!! ${e.message}")
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("tetetetete", "writeData IOException!! ${e.message}")
            }
        }
//        return file
    }
}