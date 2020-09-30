package com.zzq.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter : RecyclerView.Adapter<ListAdapter.ItemHolder>() {

    private val data = ArrayList<String>()

    fun setData(newData: ArrayList<String>) {
        data.clear()
        data.addAll(newData)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_string, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindData(data[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ItemHolder(itemView:View) :RecyclerView.ViewHolder(itemView) {

        val tvItemText = itemView.findViewById<TextView>(R.id.tv_item_text)
        fun bindData(data:String) {
            tvItemText.text = data
        }
    }
}

