package com.zzq.saf.ui.read

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.zzq.common.utils.showToast
import com.zzq.saf.R
import com.zzq.saf.databinding.FragmentReadBinding
import com.zzq.saf.utils.StorageUtil.printStorageInfo
import kotlinx.coroutines.launch

class ReadFragment : Fragment() {

    private lateinit var readViewModel: ReadViewModel

    private lateinit var btnRead1: Button
    private lateinit var btnRead2: Button
    private lateinit var tvReadInfo:TextView

    private val CODE_1= 0x200
    private val CODE_2= 0x300

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentReadBinding>(inflater,
                R.layout.fragment_read, container, false)
        readViewModel = ViewModelProvider(this).get(ReadViewModel::class.java)
        btnRead1 = dataBinding.btnRead1
        btnRead2 = dataBinding.btnRead2
        tvReadInfo = dataBinding.tvReadInfo

        dataBinding.read1Listener = View.OnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            // you can set type to filter files to show
            intent.type = "*/*"
            startActivityForResult(intent, CODE_1)
        }
        dataBinding.read2Listener = View.OnClickListener { val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            // you can set type to filter files to show
            intent.type = "*/*"
            startActivityForResult(intent, CODE_2) }

        printStorageInfo()
        return dataBinding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null || data.data == null) {
            //未选择文件
            showToast("未选择文件")
            return
        }
        if (requestCode == CODE_1) {
            val uri = data.data!!
            //TODO 如果encodePath有可能为null，那么有没有其他方式完成？
            val fileName = uri.encodedPath
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            lifecycleScope.launch {
                tvReadInfo.text = "读取1 正在解析。。。。。。"
                val readFileContent = readViewModel.dealFileData(inputStream)
                tvReadInfo.text = fileName + "\n $readFileContent"
            }
        } else if (requestCode == CODE_2) {
            val uri = data.data!!
            tvReadInfo.text = "读取2 正在解析。。。。。。"
            tvReadInfo.text = dumpImageMetaData(uri)
        }
    }


    /**
     * Grabs metadata for a document specified by URI, logs it to the screen.
     *
     * @param uri The uri for the document whose metadata should be printed.
     */
    fun dumpImageMetaData(uri: Uri):String {

        var resultUri = StringBuilder("uri.encodePath: ${uri.encodedPath}, \n\nuri.toString: ${uri.toString()}")
        // BEGIN_INCLUDE (dump_metadata)

        // 由于该查询仅适用于单个文档，因此将仅返回一行。 不需要过滤，排序或选择字段，因为我们希望一个文档的所有字段。
        val cursor = requireActivity().contentResolver
                .query(uri, null, null, null, null, null)
        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // 请注意，它称为“DISPLAY_NAME”。这是特定于提供程序的，不一定是文件名。
                val displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                Log.i("ReadFragment", "Display Name: $displayName")
                resultUri.append("\n\nDisplayName: $displayName")

                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                // 如果大小未知，则存储的值为null。但是由于int在java中不能为null，因此行为是特定于实现的，
                // 这只是“不可预测”的幻想。因此，通常，在分配给int之前检查它是否为null。
                // 这经常发生：存储API允许远程文件，其大小可能在本地未知
                var size: String? = null
                size = if (!cursor.isNull(sizeIndex)) {
                    // 从技术上讲，该列存储一个int，但cursor.getString会自动进行转换
                    cursor.getString(sizeIndex)
                } else {
                    "Unknown"
                }
                resultUri.append("\n\nsize:$size")
                Log.i("ReadFragment", "Size: $size")
            }
        } finally {
            cursor?.close()
        }
        // END_INCLUDE (dump_metadata)

        return resultUri.toString()
    }
}