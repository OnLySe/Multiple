package com.zzq.jetpack.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zzq.jetpack.R
import com.zzq.jetpack.base.BaseViewHolder
import com.zzq.jetpack.bean.ListItemType

class SecondTypeAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private val dataList = ArrayList<ListItemType>()

    fun addNewData(newData: ArrayList<ListItemType>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    fun addData(itemData: ListItemType) {
        dataList.add(itemData)
        notifyItemInserted(dataList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_second_type, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val itemData = dataList[holder.adapterPosition]
        holder.setText(Pair(R.id.tv_item_type_second_title,itemData.text))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

class ThirdTypeAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private val dataList = ArrayList<ListItemType>()

    fun addNewData(newData: ArrayList<ListItemType>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    fun addData(itemData: ListItemType) {
        dataList.add(itemData)
        notifyItemInserted(dataList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_third_type, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val itemData = dataList[holder.adapterPosition]
        holder.setText(Pair(R.id.tv_item_type_third_title,itemData.text))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

