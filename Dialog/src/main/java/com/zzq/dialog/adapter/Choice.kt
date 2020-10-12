package com.zzq.dialog.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.RecyclerView
import com.zzq.dialog.R

internal object CheckPayload
internal object UncheckPayload

internal class ListAdapter() : RecyclerView.Adapter<ListAdapter.ItemHolder>() {

    private val data = ArrayList<CompanyEntity>()
    var currentSelection = -1
        private set(value) {
            if (value == field) return
            val previousSelection = field
            field = value

            //注意：在这里修改entity的isChecked属性，因为这个属性值不会对控件的checked状态产生影响！
            if (previousSelection >= 0) {
                data[previousSelection].isChecked = false
            }
            data[value].isChecked = true

            notifyItemChanged(previousSelection, UncheckPayload)
            notifyItemChanged(value, CheckPayload)
        }

    fun setData(newData: ArrayList<CompanyEntity>) {
        data.clear()
        data.addAll(newData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_company, parent, false)
        return ItemHolder(view, this)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.radioButton.setOnCheckedChangeListener(null)
        holder.bindData(data[holder.adapterPosition])
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int, payloads: MutableList<Any>) {
        when (payloads.firstOrNull()) {
            CheckPayload -> {
                holder.radioButton.isChecked = true
            }

            UncheckPayload -> {
                holder.radioButton.isChecked = false
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal fun itemClicked(index: Int) {
        this.currentSelection = index
    }

    class ItemHolder(itemView: View, private val adapter: ListAdapter) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }
        val tvItemText = itemView.findViewById<TextView>(R.id.tv_item_text)
        val radioButton = itemView.findViewById<AppCompatRadioButton>(R.id.rb_select)

        fun bindData(data: CompanyEntity) {
            Log.e("tetetetete", "bindData: ${data.toString()}")
            tvItemText.text = data.name
        }

        override fun onClick(v: View?) {
            if (adapterPosition < 0) return
            adapter.itemClicked(adapterPosition)
        }
    }
}