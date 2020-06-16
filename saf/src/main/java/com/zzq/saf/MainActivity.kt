package com.zzq.saf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    private val codeCreateFile = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        createFile("Main.txt")

        createFile("saf_2.txt","MainActivity end!")
    }

    private fun createFile(fileName: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as
            // a file (as opposed to a list of contacts or timezones).
            addCategory(Intent.CATEGORY_OPENABLE)

            // Create a file with the requested MIME type.
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, fileName)
        }

        startActivityForResult(intent, codeCreateFile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == codeCreateFile) {
            data?.let {
                val uri = it.data
                Log.e("tetetetete", uri.toString())
            }
        }
    }

    /**
     * @param fileName 文件名
     * @param text 写入文件的内容
     */
    private fun createFile(fileName: String, text: String) {
        val filesDir: File = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        if (!filesDir.exists()) {
            if (filesDir.mkdirs()) {
            }
        }
        val file = File(filesDir, fileName)

        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw IOException("Cant able to create file")
                }
            }
            Log.e("tetetetete", file.absolutePath)
            val os: OutputStream = FileOutputStream(file)
            val data = text.toByteArray()
            os.write(data)
            os.close()
            Log.e("tetetetete", "File Path= " + file.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
