package com.zzq.jetpack.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.zzq.jetpack.Config
import com.zzq.jetpack.R
import com.zzq.jetpack.bean.ClickFunction

class FunctionsAdapter(private val data: List<ClickFunction>) : RecyclerView.Adapter<FunctionsAdapter.FunctionViewHolder>() {


    class FunctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btn = itemView.findViewById<Button>(R.id.btn_function)
        fun bindData(itemData: ClickFunction) {
            btn.text = itemData.title
            btn.setOnClickListener(itemData.action)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionViewHolder {
        return if (viewType == Config.TYPE_FIRST) {
            FunctionViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_function1, parent, false))
        } else {
            FunctionViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_function2, parent, false))
        }
    }

    override fun onBindViewHolder(holder: FunctionViewHolder, position: Int) {
        holder.bindData(data[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}