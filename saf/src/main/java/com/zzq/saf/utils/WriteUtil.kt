package com.zzq.saf.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
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
     * 将图片写入到外部存储的公共目录下
     */
    suspend fun imageWritePublicDirectory(context: Context, fileName: String, bitmap: Bitmap): Uri? = withContext(Dispatchers.IO) {
        //注意：就目前而言（20200922），如果写入图片前，指定路径就已经有相同文件名文件，添加相同命名文件时，系统会在文件名后缀添加(1)等序号！！！

        //设置保存参数到ContentValues中
        val contentValues = ContentValues()
        //设置文件名
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        //兼容Android Q和以下版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
            //RELATIVE_PATH是相对路径不是绝对路径
            //DCIM是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，不可以写没存在的名字
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/zzqSAF")
            //contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Music/signImage");
        } else {
            contentValues.put(MediaStore.Images.Media.DATA,
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path)
        }
        //设置文件类型
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/JPEG")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "created from zzqSAF")
//                contentValues.put(MediaStore.Files.FileColumns.TITLE, publicFileName)
        //执行insert操作，向系统文件夹中添加文件
        //EXTERNAL_CONTENT_URI代表外部存储器，该值不变
        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri == null) {
            return@withContext null
        } else {
            Log.e("tetetetete", "insert image uri: ${uri.toString()}")
            //若生成了uri，则表示该文件添加成功。使用流将内容写入该uri中即可
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
            }
            return@withContext uri
        }
    }

    private suspend fun writeData(file: File, content: String): File {

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
}