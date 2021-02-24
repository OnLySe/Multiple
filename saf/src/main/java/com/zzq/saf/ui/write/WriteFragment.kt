package com.zzq.saf.ui.write

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.zzq.common.utils.TimeUtil
import com.zzq.common.utils.getDataFromSp
import com.zzq.common.utils.saveDataToSp
import com.zzq.common.utils.showToast
import com.zzq.saf.R
import com.zzq.saf.databinding.FragmentWriteBinding
import com.zzq.saf.utils.WriteUtil
import com.zzq.saf.utils.createFile
import com.zzq.saf.utils.createRandomString
import kotlinx.coroutines.launch
import java.net.URI


class WriteFragment : Fragment() {

    private lateinit var writeViewModel: WriteViewModel
    private lateinit var dataBinding: FragmentWriteBinding
    private lateinit var tvInfo1: TextView
    private lateinit var tvInfo2: TextView
    private lateinit var tvInfo3: TextView

    private val SP_NAME = "Write"
    private val SP_KEY = "write_root"
    private var uriData: String? = null

    private val codeCreateFile = 123
    private val codeRootDirectory = 183

    /***创建在根目录中的文件夹名**/
    private val FOLD_NAME = "zzqSAF"
    private val FILE_NAME = "20200921"

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
        tvInfo3 = dataBinding.tvInfo3

        uriData = getDataFromSp(SP_NAME, SP_KEY)
        if (uriData != null && uriData!!.isNotEmpty()) {
            tvInfo2.text = uriData
        }
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
            getRootDirectory()
        }
        dataBinding.writeClickListener3 = View.OnClickListener {
            createPublicFile()
        }
    }

    /**
     * 向公有目录插入文件，如DCIM、Music等
     */
    private fun createPublicFile() {
        lifecycleScope.launch {
            val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.insert1)
            val uri = WriteUtil.imageWritePublicDirectory(requireContext(),
                    "IMG_${TimeUtil.getMinuteString()}", bitmap)
            if (uri == null) {
                tvInfo3.text = "未能成功返回URI"
            } else {
                tvInfo3.text = uri.toString()
            }
        }
    }

    private fun getRootDirectory() {
        if (uriData != null && uriData!!.isNotEmpty()) {
            val uri = Uri.parse(uriData)
            val fromSingleUri = DocumentFile.fromSingleUri(requireContext(), uri)!!
            if (!fromSingleUri.exists()) {
                showToast("位于文件路径上的文件不存在，需要创建！")
                goDocumentTree()
                return
            }
            writeRootData(fromSingleUri, "nextOne nextOne nextOne")
        } else {
            goDocumentTree()
        }
    }

    private fun goDocumentTree() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        /**
         * 在https://developer.android.com/training/data-storage/shared/documents-files#persist-permissions中：
         * 当您的应用打开文件进行读取或写入时，系统会向应用授予对该文件的 URI 的访问权限，该授权在用户重启设备
         * 之前一直有效，如需在设备重启后保留对文件的访问权限并提供更出色的用户体验，您的应用可以“获取”系统提供
         * 的永久性 URI 访问权限，即：Intent.FLAG_GRANT_READ_URI_PERMISSION orIntent.FLAG_GRANT_WRITE_URI_PERMISSION
         */
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        startActivityForResult(intent, codeRootDirectory)
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
                if (data != null) {
                    createFile(data.data, FOLD_NAME, FILE_NAME) {
                        writeRootData(it, "onActivityForResult!")
                    }
                }
            } else {
                showToast("未选择文件夹或文件")
            }
        }
    }

    private fun writeRootData(uri: DocumentFile, content: String) {
        val javaUri = URI(uri.uri.toString())
        uriData = javaUri.toString()
        saveDataToSp(SP_NAME, SP_KEY, uriData!!)
        tvInfo2.text = uriData!!
        Log.e("tetetetete", "uri: " + uri.uri.toString() + " content: $content")
        lifecycleScope.launch {
            WriteUtil.textWriteRootDirectory(requireContext(), uri, content)
        }
    }
}