package com.minew.navigation

import android.view.View
import androidx.fragment.app.Fragment
import com.minew.navigation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class FirstDetailFragment : BaseFragment() {
    override fun getLayoutId(): Int =R.layout.fragment_first_detail

    override fun initView(view: View) {

        setToolbarReturnFun()
    }

}
