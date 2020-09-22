package com.zzq.saf.ui.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.zzq.saf.R

class OtherFragment : Fragment() {

    private lateinit var otherViewModel: OtherViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        otherViewModel = ViewModelProvider(this).get(OtherViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_other, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        otherViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}