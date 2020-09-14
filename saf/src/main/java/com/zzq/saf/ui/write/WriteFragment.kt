package com.zzq.saf.ui.write

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.zzq.saf.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class WriteFragment : Fragment() {

    private lateinit var writeViewModel: WriteViewModel

    private val codeCreateFile = 123
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        writeViewModel = ViewModelProvider(this).get(WriteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_write, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        writeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        createFile("saf_2.txt", "MainActivity end!")
        return root
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
        val filesDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!
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