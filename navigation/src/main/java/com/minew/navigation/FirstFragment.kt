package com.minew.navigation

import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar
import com.minew.navigation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class FirstFragment : BaseFragment(),View.OnClickListener {
    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this).navigationBarColor(R.color.btn13)
    }

    override fun getLayoutId(): Int = R.layout.fragment_first

    override fun initView(view: View) {
        setToolbarReturnFun()
        val btnToDetail: Button = view.findViewById(R.id.btn_to_first_detail)
        btnToDetail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btn_to_first_detail -> {
                    navigate(R.id.action_firstFragment_to_firstDetailFragment)
                }
            }
        }
    }

}
