package com.zzq.saf.ui.write

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.zzq.saf.R
import com.zzq.saf.databinding.FragmentWriteBinding
import com.zzq.saf.utils.WriteUtil
import com.zzq.saf.utils.createRandomString
import com.zzq.util.TimeUtil
import com.zzq.util.showToast
import kotlinx.coroutines.launch


class WriteFragment : Fragment() {

    private lateinit var writeViewModel: WriteViewModel
    private lateinit var dataBinding: FragmentWriteBinding
    private lateinit var tvInfo1: TextView
    private lateinit var tvInfo2: TextView

    private val codeCreateFile = 123
    private val codeRootDirectory = 183
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        writeViewModel = ViewModelProvider(this).get(WriteViewModel::class.java)
        dataBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_write, container, false)
        tvInfo1 = dataBinding.tvInfo1
        tvInfo2 = dataBinding.tvInfo2
        initListener()
        return dataBinding.root
    }

    private fun initListener() {
        dataBinding.writeClickListener1 = View.OnClickListener {
            lifecycleScope.launch {
                val string = createRandomString(1000)
                val file = WriteUtil.textWritePrivateDirectory(requireContext(),
                        "${TimeUtil.getTodayString()}_write1.txt", string)
                tvInfo1.text = "path: ${file.absolutePath}\nlength: ${file.length()}"
            }
        }
        dataBinding.writeClickListener2 = View.OnClickListener {
            lifecycleScope.launch {
                val string = createRandomString(1000)
//                WriteUtil.textWriteRootDirectory(requireActivity().application,
//                        "${TimeUtil.getTodayString()}_write1.txt", string)

//                WriteUtil.test()
                getRootDirectory()
            }
        }
    }

    private fun getRootDirectory() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        //Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        startActivityForResult(intent, codeRootDirectory)
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
        } else if (requestCode == codeRootDirectory) {
            if (resultCode == Activity.RESULT_OK) {
                var uri: Uri? = null
                if (data != null) {
                    uri = data.data
                    //返回的路径是：content://com.android.externalstorage.documents/tree/primary%3A这样形式
                    Log.e("tetetetete", "onActivityResult root: ${uri.toString()}")

                    val fromSingleUri = DocumentFile.fromSingleUri(requireContext(), uri!!)
                    Log.e("tetetetete", "onActivityResult root real: ${fromSingleUri!!.name}")

//                    requireActivity().contentResolver.takePersistableUriPermission(
//                            uri,
//                            Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    )
                    DocumentFile.fromTreeUri(requireContext(), uri)!!.listFiles().forEach {
                        Log.e("tetetetete", "onActivityResult forEach: ${it.name}")
                    }


                    DocumentFile.fromTreeUri(requireContext(),uri)!!.createDirectory("zzqSAF")

                }
            } else {
                showToast("获取失败")
            }
        }
    }
}