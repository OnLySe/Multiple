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

    /***创建在根目录中的文件夹名**/
    private val FOLD_NAME = "zzqSAF"

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
                    val fromTreeUri = DocumentFile.fromTreeUri(requireContext(), uri)!!
                    //用来标记是否已经存在指定文件夹
                    var hasNeedFolder = false

                    //标记已经创建文件夹，但是有没有创建文件
                    var hasNeedFile = false

                    var childFileUri = fromTreeUri
                    fromTreeUri.listFiles().forEach {
                        Log.e("tetetetete", "onActivityResult forEach: ${it.name}")
                        if (it.name.equals(FOLD_NAME)) {
                            hasNeedFolder = true

                        }
                    }
                    if (hasNeedFolder) {

                        //已经创建文件夹，判断有没有创建文件
                        if (hasNeedFile) {
                            //如果已经有指定文件，可直接写入文件


                        } else {
                            //没有文件，可能未创建，或者被删除，那么需要创建文件
                            //创建纯文本类型文件
                            //createFile返回的正式我们需要的子目录，所以在这里赋值并把值传出去
                            //TODO 这里也应该对创建文件失败后做处理
                            childFileUri = fromTreeUri.createFile("text/plain", FOLD_NAME)!!
                        }

                        writeRootData(childFileUri, "content")
                    } else {
                        //不存在指定文件夹就创建

                        val foldUri = fromTreeUri.createDirectory(FOLD_NAME)
                        if (foldUri == null) {
                            showToast("创建子目录${FOLD_NAME}失败")
                        } else {
                            //创建纯文本类型文件
                            //createFile返回的正式我们需要的子目录，所以在这里赋值并把值传出去
                            val childFileUri = foldUri.createFile("text/plain", FOLD_NAME)!!
                            writeRootData(childFileUri, "content")
                        }
                    }
                    //在已经存在zzqSaf文件夹的情况下，重复调用createDirectory()创建文件夹也会成功，生成如zzqSAF(1)这样的文件夹
//                    DocumentFile.fromTreeUri(requireContext(),uri)!!.createDirectory("zzqSAF")


                }
            } else {
                showToast("未选择文件夹或文件")
            }
        }
    }

    private fun writeRootData(uri: DocumentFile, content: String) {
        lifecycleScope.launch {
            WriteUtil.textWriteRootDirectory(requireContext(),uri,content)
        }
    }
}