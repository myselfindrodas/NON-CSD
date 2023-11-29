package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.QtyAlertListData
import com.grocery.sainik_grocery.R

class QtyAlertListAdapter(val context: Context, var onItemClickListener: OnItemClickListener)
    :RecyclerView.Adapter<QtyAlertListAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var quantityArray: ArrayList<QtyAlertListData> = arrayListOf()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, tvQuantity: TextView, data: QtyAlertListData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QtyAlertListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.qty_alert_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: QtyAlertListAdapter.classViewHolder, position: Int) {
        holder.tvQuantity.text = quantityArray[position].tvQuantity
        holder.tvRs.text = quantityArray[position].tvRs

        holder.itemView.setOnClickListener{
            onItemClickListener.onClick(position, it, holder.tvQuantity, quantityArray[position])
        }
    }

    override fun getItemCount(): Int {
        return quantityArray.size
    }

    fun updateQuantityData(mQuantityArray: ArrayList<QtyAlertListData>) {
        quantityArray = mQuantityArray
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvQuantity: TextView
        var tvRs: TextView
        init {
            tvQuantity = itemView.findViewById(R.id.tvQuantity)
            tvRs = itemView.findViewById(R.id.tvRs)
        }
    }
}