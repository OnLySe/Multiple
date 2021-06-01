package com.zzq.jetpack.livedata

import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzq.common.interfaces.ClickProxy
import com.zzq.common.utils.showToast
import com.zzq.jetpack.R
import com.zzq.jetpack.adapter.TextAdapter
import com.zzq.jetpack.base.BaseFragment
import com.zzq.jetpack.livedata.vm.LiveDataViewModel
import com.zzq.jetpack.livedata.vm.LiveDataViewModelFactory
import com.zzq.jetpack.utils.RandomDataUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LiveDataMainFragment : BaseFragment(), View.OnClickListener {

    private lateinit var rvItems: RecyclerView

    private val liveDataViewModel: LiveDataViewModel by viewModels { LiveDataViewModelFactory }

    private val textAdapter = TextAdapter()

    private val textLiveData = MutableLiveData<String>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_livedata_initial
    }

    override fun initView(view: View) {
        setToolbarReturnFun()
        view.findViewById<Button>(R.id.btn1).setOnClickListener(ClickProxy(this))
        view.findViewById<Button>(R.id.btn2).setOnClickListener(ClickProxy(this))
        view.findViewById<Button>(R.id.btn3).setOnClickListener(ClickProxy(this))
        view.findViewById<Button>(R.id.btn4).setOnClickListener(ClickProxy(this))

        rvItems = view.findViewById(R.id.rv_items)
        rvItems.layoutManager = LinearLayoutManager(requireContext())
        rvItems.adapter = textAdapter

        textLiveData.observe(this, Observer {
            addListData("1号收到$it!")
        })

        textLiveData.observeForever {
            addListData("forever 收到$it")
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(1000)
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
            R.id.btn3 -> {
                addListData("开始Flow！")
                liveDataViewModel.currentResult.observe(this, Observer {
                    addListData(it)
                })
            }
            R.id.btn4 -> {
                val mutableLiveData = MutableLiveData<Int>()
                mutableLiveData.observe(this,{})
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