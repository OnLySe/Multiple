package com.zzq.navigation

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zzq.navigation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class SecondDetailFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_second_detail

    override fun initView(view: View) {
        setToolbarReturnFun()
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = "Second Detail Fragment"

    }

}
