package com.zzq.jetpack.base

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.zzq.jetpack.bean.ClickFunction

abstract class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindData(data: ClickFunction)
}

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setText(vararg pairs: Pair<Int, String>) {
        for ((@IdRes key, value) in pairs) {
            itemView.findViewById<TextView>(key).text = value
        }
    }

    fun setOnClickListener(vararg listeners: Pair<Int, (View) -> Unit>) {
        for ((@IdRes key, value) in listeners) {
            itemView.findViewById<View>(key).setOnClickListener { value(it) }
        }
    }
}