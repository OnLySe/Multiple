package com.zzq.saf.ui.write

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.zzq.saf.R
import com.zzq.saf.utils.WriteUtil
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            WriteUtil.writeDocument(requireContext(),"saf2.txt","WriteFragment")
        }
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
}