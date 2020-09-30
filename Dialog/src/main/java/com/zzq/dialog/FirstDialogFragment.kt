package com.zzq.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * List Dialog
 */
class FirstDialogFragment : DialogFragment() {

    private lateinit var rvList : RecyclerView
    private lateinit var adapter:ListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.dialog_fragment_first, container, false)
        rvList = view.findViewById(R.id.rv_first)
        rvList.layoutManager = LinearLayoutManager(requireContext())
        createAdapter()

        rvList.adapter = adapter
        return view
    }

    fun createAdapter() {
        adapter = ListAdapter()
        val list = arrayListOf<String>("1","2","3","4","5")
        adapter.setData(list)
    }
}