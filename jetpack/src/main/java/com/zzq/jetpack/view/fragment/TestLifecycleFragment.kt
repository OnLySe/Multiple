package com.zzq.jetpack.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zzq.jetpack.lifecycle.LifecycleModel

class TestLifecycleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val lifecycleModel = LifecycleModel(this)
        lifecycle.addObserver(lifecycleModel)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}