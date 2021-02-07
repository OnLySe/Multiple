package com.zzq.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zzq.recyclerview.R

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainHolder>() {
    private val dataList = ArrayList<String>()

    fun addNewData(newData: ArrayList<String>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    fun addData(itemData: String) {
        dataList.add(itemData)
        notifyItemInserted(dataList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bindData(dataList[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle:TextView = itemView.findViewById(R.id.tv_title)

        fun bindData(itemData: String) {
            tvTitle.text = itemData
        }
    }
}

