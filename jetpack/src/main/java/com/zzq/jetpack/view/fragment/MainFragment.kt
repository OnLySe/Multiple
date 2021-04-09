package com.zzq.jetpack.view.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzq.common.utils.showToast
import com.zzq.jetpack.R
import com.zzq.jetpack.app.Config
import com.zzq.jetpack.base.BaseFragment
import com.zzq.jetpack.bean.ClickFunction
import com.zzq.jetpack.lifecycle.MyLifeCycleService
import com.zzq.jetpack.list.FunctionsAdapter
import com.zzq.jetpack.view.activity.TestLifecycleActivity

class MainFragment : BaseFragment() {
    private lateinit var rvItems: RecyclerView
    private lateinit var serviceIntent: Intent
    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initView(view: View) {
        rvItems = view.findViewById(R.id.rv_items)
        rvItems.layoutManager = LinearLayoutManager(requireContext())
        val list = createData()
        rvItems.adapter = FunctionsAdapter(list)
    }

    private fun createData(): List<ClickFunction> {
        val list = ArrayList<ClickFunction>()
        list.add(ClickFunction("Lifecycle", {}, Config.TYPE_TITLE))
        list.add(ClickFunction("Activity与Lifecycle", { startActivity(Intent(requireContext(), TestLifecycleActivity::class.java)) }))
        list.add(ClickFunction("Service与Lifecycle", { showToast("展示Service与Lifecycle关系") }))
        serviceIntent = Intent(requireContext(), MyLifeCycleService::class.java)
        list.add(ClickFunction("startService", { requireContext().startService(serviceIntent) }, Config.TYPE_SECOND))
        list.add(ClickFunction("stopService", { requireContext().stopService(serviceIntent) }, Config.TYPE_SECOND))

        list.add(ClickFunction("LiveData", {}, Config.TYPE_TITLE))
        list.add(ClickFunction("LiveData initial", { navigate(R.id.action_mainFragment_to_liveDataMainFragment) }, Config.TYPE_FIRST))

        list.add(ClickFunction("ViewPager2", {}, Config.TYPE_TITLE))

        list.add(ClickFunction("Navigation", {}, Config.TYPE_TITLE))
        list.add(ClickFunction("Navigation跳转Fragment", { navigate(R.id.action_mainFragment_to_homeFragment) }, Config.TYPE_FIRST))

        list.add(ClickFunction("RecyclerView", {}, Config.TYPE_TITLE))
        list.add(ClickFunction("RecyclerView itemDecoration", { navigate(R.id.action_mainFragment_to_rvItemDecorationFragment) }, Config.TYPE_FIRST))
        list.add(ClickFunction("RecyclerView ConcatAdapter", { navigate(R.id.action_mainFragment_to_rvMergeAdapterFragment) }, Config.TYPE_FIRST))

        list.add(ClickFunction("Room", {showToast("具体看下方Room使用")}, Config.TYPE_TITLE))
        list.add(ClickFunction("Room Add Item", { navigate(R.id.action_mainFragment_to_roomMainFragment) }, Config.TYPE_SECOND))

        return list
    }

}