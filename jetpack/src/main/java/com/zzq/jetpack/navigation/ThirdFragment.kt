package com.zzq.jetpack.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.zzq.jetpack.R
import com.zzq.jetpack.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class ThirdFragment : BaseFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(requireContext(), "onBackPressed",Toast.LENGTH_SHORT).show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)

        Log.e("tetetetete", "ThirdFragment ${toString()} onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("tetetetete", "ThirdFragment ${toString()} onDetach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("tetetetete", "ThirdFragment ${toString()} onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.e("tetetetete", "ThirdFragment ${toString()} onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.e("tetetetete", "ThirdFragment ${toString()} onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.e("tetetetete", "ThirdFragment ${toString()} onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("tetetetete", "ThirdFragment ${toString()} onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("tetetetete", "ThirdFragment ${toString()} onDestroyView")
    }

    override fun getLayoutId(): Int {
        Log.e("tetetetete", "ThirdFragment ${toString()} onCreateView")
        return R.layout.fragment_third
    }

}
