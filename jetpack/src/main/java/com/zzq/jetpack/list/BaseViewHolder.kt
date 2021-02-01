package com.zzq.jetpack.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zzq.jetpack.bean.ClickFunction

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindData(data:ClickFunction)

}