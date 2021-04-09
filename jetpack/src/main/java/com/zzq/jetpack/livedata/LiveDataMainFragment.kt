package com.zzq.jetpack.livedata

import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzq.common.interfaces.ClickProxy
import com.zzq.common.utils.showToast
import com.zzq.jetpack.R
import com.zzq.jetpack.adapter.TextAdapter
import com.zzq.jetpack.base.BaseFragment
import com.zzq.jetpack.utils.RandomDataUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LiveDataMainFragment : BaseFragment(), View.OnClickListener {

    private lateinit var btnPostValue: Button
    private lateinit var btnClearData: Button
    private lateinit var rvItems:RecyclerView

    private val textAdapter = TextAdapter()

    private val textLiveData = MutableLiveData<String>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_livedata_initial
    }

    override fun initView(view: View) {
        setToolbarReturnFun()
        btnPostValue = view.findViewById(R.id.btn1)
        btnPostValue.setOnClickListener(ClickProxy(this))
        btnClearData = view.findViewById(R.id.btn2)
        btnClearData.setOnClickListener(ClickProxy(this))

        rvItems = view.findViewById(R.id.rv_items)
        rvItems.layoutManager = LinearLayoutManager(requireContext())
        rvItems.adapter = textAdapter

        textLiveData.observe(this, {
            addListData("1号收到$it!")
        })

        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000)
            textLiveData.observe(this@LiveDataMainFragment, {
                addListData("2号收到$it!")
            })
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn1 -> {
                val data = "postData:${RandomDataUtil.nextInt()}"
                addListData(data)
                textLiveData.value = data
            }
            R.id.btn2 -> {
                if (textAdapter.itemCount == 0) {
                    showToast("当前无数据")
                    return
                }
                clearAllData()
                showToast("清除数据完成")
            }
        }
   }

    private fun addListData(data: String) {
        textAdapter.addItemData(data)
        rvItems.smoothScrollToPosition(textAdapter.itemCount)
    }

    private fun clearAllData() {
        textAdapter.clearAllData()
        rvItems.smoothScrollToPosition(0)
    }
}