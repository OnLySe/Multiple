package com.zzq.jetpack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zzq.jetpack.R

internal class TextAdapter : RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

    private val textDataList = ArrayList<String>()

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvText: TextView = itemView.findViewById(R.id.tv_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false))
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.tvText.text = textDataList[position]
    }

    override fun getItemCount(): Int {
        return textDataList.size
    }

    fun addItemData(data: String) {
        textDataList.add(data)
        notifyItemInserted(textDataList.size)
    }

    fun clearAllData() {
        textDataList.clear()
        notifyDataSetChanged()
    }
}