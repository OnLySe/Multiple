package com.zzq.jetpack.recyclerview

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzq.common.utils.DensityUtil
import com.zzq.jetpack.R
import com.zzq.jetpack.base.BaseFragment
import com.zzq.jetpack.bean.ListItemType
import com.zzq.jetpack.recyclerview.adapter.MainAdapter
import com.zzq.jetpack.recyclerview.adapter.SecondTypeAdapter
import com.zzq.jetpack.recyclerview.adapter.ThirdTypeAdapter
import com.zzq.recyclerview.decoration.VerticalProgressDecoration
import java.util.*
import kotlin.collections.ArrayList

class RvMergeAdapterFragment : BaseFragment(), View.OnClickListener {

    private val mainAdapter = MainAdapter()
    private val secondTypeAdapter = SecondTypeAdapter()
    private val thirdTypeAdapter = ThirdTypeAdapter()
    private val adapter = ConcatAdapter(mainAdapter, secondTypeAdapter, thirdTypeAdapter)
    private lateinit var rvMain: RecyclerView
    val random = Random()
    override fun getLayoutId(): Int {
        return R.layout.fragment_recyclerview_merge_adapter
    }

    override fun initView(view: View) {
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        tvTitle.text = "ConcatAdapter"

        rvMain = view.findViewById(R.id.rv_fun_merge)
        rvMain.layoutManager = LinearLayoutManager(requireContext())
        rvMain.adapter = adapter
        val verticalProgressDecoration = VerticalProgressDecoration(requireContext())
        verticalProgressDecoration.bottomMargin = DensityUtil.dp2px(requireContext(), 10F)
        rvMain.addItemDecoration(verticalProgressDecoration)
        mainAdapter.addNewData(createData())

        view.findViewById<Button>(R.id.btn_add).setOnClickListener(this)
    }

    private fun createData(): ArrayList<String> {
        val list = ArrayList<String>()
        repeat(10) {
            list.add("data: ${random.nextInt(50) + 10}")
        }
        return list
    }

    fun addData() {
        val randomValue = random.nextInt(50) + 10
        mainAdapter.addData("data: $randomValue")
        secondTypeAdapter.addData(ListItemType("second: $randomValue", 2))
        thirdTypeAdapter.addData(ListItemType("third: $randomValue", 3))
//        rvMain.smoothScrollToPosition(mainAdapter.itemCount)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> {
                addData()
            }
        }
    }
}