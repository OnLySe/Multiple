package com.zzq.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzq.dialog.adapter.CompanyEntity
import com.zzq.dialog.adapter.ListAdapter
import com.zzq.util.showToast

/**
 * List Dialog
 * DialogFragment显示RecyclerView，其宽高、底部间距、位置等，都可以在onStart中通过{@link WindowManager.LayoutParams}设置
 */
class SingleChoiceDialogFragment : DialogFragment(),View.OnClickListener {

    private lateinit var rvList: RecyclerView
    private lateinit var adapter: ListAdapter

    private val companyList = ArrayList<CompanyEntity>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.dialog_fragment_first, container, false)
        rvList = view.findViewById(R.id.rv_first)
        rvList.layoutManager = LinearLayoutManager(requireContext())
        createAdapter()

        rvList.adapter = adapter

        view.findViewById<TextView>(R.id.tv_dialog_cancel).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tv_dialog_ok).setOnClickListener(this)
        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            window?.let {
                val dm = DisplayMetrics()
                //设置背景透明
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                activity!!.windowManager.defaultDisplay.getMetrics(dm)
                window.setLayout((dm.widthPixels * 0.8).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }

    private fun createAdapter() {
        adapter = ListAdapter()
        companyList.add(CompanyEntity("Google", false))
        companyList.add(CompanyEntity("Baidu", false))
        companyList.add(CompanyEntity("Alibaba", false))
        companyList.add(CompanyEntity("Tencent", false))
        companyList.add(CompanyEntity("Facebook", false))
        adapter.setData(companyList)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_dialog_cancel -> {
                dismiss()
            }

            R.id.tv_dialog_ok -> {
                if (adapter.currentSelection < 0) {
                    showToast("未选择")
                    return
                }
                showToast("当前选中 ${companyList[adapter.currentSelection]}")
                dismiss()
            }
        }
    }

    override fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            super.dismiss()
        }
    }
}