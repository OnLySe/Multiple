package com.minew.navigation

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.minew.navigation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class SecondFragment : BaseFragment(), View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_second

    override fun initView(view: View) {
        setToolbarReturnFun()
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = "SecondFragment"

        view.findViewById<Button>(R.id.btn_to_first).setOnClickListener(this)
        view.findViewById<Button>(R.id.to_second_detail).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == null) {
            return
        }
        when (v.id) {
            R.id.btn_to_first -> {
                Snackbar.make(v,"还未开发", Snackbar.LENGTH_SHORT).show()
            }

            R.id.to_second_detail -> {
                navigate(R.id.action_secondFragment_to_secondDetailFragment)
            }
        }
    }

}
