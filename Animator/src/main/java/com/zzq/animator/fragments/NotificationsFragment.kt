package com.zzq.animator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zzq.animator.R
import com.zzq.animator.databinding.FragmentNotificationsBinding
import com.zzq.animator.vm.ViewPropertyViewModel

class NotificationsFragment : Fragment() {

    private lateinit var viewModel: ViewPropertyViewModel
    private lateinit var textNotifications1: TextView
    private lateinit var textNotifications2: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ViewPropertyViewModel::class.java)
        val dataBinding = DataBindingUtil.inflate<FragmentNotificationsBinding>(inflater,R.layout.fragment_notifications,container,false)
        textNotifications1 = dataBinding.textNotifications1
        textNotifications2 = dataBinding.textNotifications2
        return dataBinding.root
    }
}