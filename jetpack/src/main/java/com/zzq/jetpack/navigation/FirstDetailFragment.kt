package com.zzq.jetpack.navigation

import android.view.View
import androidx.fragment.app.Fragment
import com.zzq.jetpack.R
import com.zzq.jetpack.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class FirstDetailFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_first_detail

    override fun initView(view: View) {

        setToolbarReturnFun()
    }

}
