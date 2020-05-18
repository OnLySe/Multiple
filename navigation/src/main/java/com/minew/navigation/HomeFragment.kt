package com.minew.navigation

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.minew.navigation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Use the [homeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment(),View.OnClickListener {
    override fun getLayoutId(): Int =R.layout.fragment_home

    override fun initView(view: View) {
        val btnFirst: Button = view.findViewById(R.id.btn_first)
        val btnSecond: Button = view.findViewById(R.id.btn_second)
        val btnThird: Button = view.findViewById(R.id.btn_third)
        btnFirst.setOnClickListener(this)
        btnSecond.setOnClickListener(this)
        btnThird.setOnClickListener(this)

        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        tvTitle.text = "Home!"
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.btn_first -> {
                    val bundle = bundleOf(Pair("start","first"), Pair("data", "show one"))
                    navigate(R.id.action_homeFragment_to_firstFragment, bundle)
                }

                R.id.btn_second -> {
                    val bundle = bundleOf(Pair("start","second"), Pair("data", "date"))
                    navigate(R.id.action_homeFragment_to_secondFragment, bundle)
                }

                R.id.btn_third -> {
                    val bundle = bundleOf(Pair("start","second"), Pair("data", "date"))
                    navigate(R.id.action_homeFragment_to_thirdFragment, bundle)
                }
            }
        }

    }
}
