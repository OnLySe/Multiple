package com.zzq.concurrent.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zzq.concurrent.R
import com.zzq.concurrent.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private val data = ArrayList<User>()

    fun updateData(data: ArrayList<User>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserIndex = itemView.findViewById<TextView>(R.id.tv_user_index)
        val tvUser = itemView.findViewById<TextView>(R.id.tv_user)
        val tvUserRate = itemView.findViewById<TextView>(R.id.tv_user_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_user_show, parent, false))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.tvUserIndex.text = holder.adapterPosition.toString()
        holder.tvUser.text = "${holder.adapterPosition} ${data[holder.adapterPosition].name}"
        holder.tvUserRate.text = data[holder.adapterPosition].rate.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}