package com.zzq.saf.ui.read

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.zzq.saf.R
import com.zzq.saf.databinding.FragmentReadBinding
import com.zzq.saf.utils.StorageUtil.printStorageInfo
import com.zzq.util.showToast
import kotlinx.coroutines.launch

class ReadFragment : Fragment() {

    private lateinit var readViewModel: ReadViewModel

    private lateinit var btnRead1: Button
    private lateinit var btnRead2: Button
    private lateinit var tvReadInfo:TextView

    private val CODE_1= 0x200

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
        dataBinding.read2Listener = View.OnClickListener { showToast("未开放") }

        printStorageInfo()
        return dataBinding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_1) {
            if (resultCode != Activity.RESULT_OK || data == null || data.data == null) {
                //未选择文件
                showToast("未选择文件")
                return
            }

            val uri = data.data!!
            //TODO 如果encodePath有可能为null，那么有没有其他方式完成？
            val fileName = uri.encodedPath
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            lifecycleScope.launch {
                val readFileContent = readViewModel.dealFileData(inputStream)
                tvReadInfo.text = fileName + "\n $readFileContent"
            }


        }
    }
}