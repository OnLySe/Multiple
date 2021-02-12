package com.zzq.jetpack.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zzq.jetpack.app.Config
import com.zzq.jetpack.R
import com.zzq.jetpack.bean.ClickFunction

class FunctionsAdapter(private val data: List<ClickFunction>) : RecyclerView.Adapter<BaseViewHolder>() {

    class FunctionViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val btn = itemView.findViewById<Button>(R.id.btn_function)
        override fun bindData(itemData: ClickFunction) {
            btn.text = itemData.title
            btn.setOnClickListener(itemData.action)
        }
    }

    class TitleHolder(itemView: View) : BaseViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        override fun bindData(itemData: ClickFunction) {
            tvTitle.text = itemData.title
            tvTitle.setOnClickListener(itemData.action)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == Config.TYPE_TITLE) {
            TitleHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_title, parent, false))
        } else if (viewType == Config.TYPE_FIRST) {
            FunctionViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_function1, parent, false))
        } else {
            //Config.TYPE_SECOND
            FunctionViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_function2, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindData(data[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}