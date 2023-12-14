package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.AppContent
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.faqmodel.Data

class FAQ_Help_Adapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener, var isFAQ:Boolean=false
) :
    RecyclerView.Adapter<FAQ_Help_Adapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var contentModelArrayList: ArrayList<Data> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(
            position: Int,
            view: View,
            id: Int,
            s: String,
            mNotificationListModelArrayList: ArrayList<Data>
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.faq_support_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.tvTitle.text = if (isFAQ){"${position + 1}. ${contentModelArrayList[position].question}"} else contentModelArrayList[position].question
        holder.tvDescriptions.text = contentModelArrayList[position].answer

        if (contentModelArrayList[position].isExpanded){
            holder.tvDescriptions.visibility=View.VISIBLE
            holder.btnMyoderexpand.rotation=270f

        }else{
            holder.tvDescriptions.visibility=View.GONE
            holder.btnMyoderexpand.rotation=90f
        }
        holder.llTitle.setOnClickListener {
            contentModelArrayList.forEach {item->
                item.isExpanded=false
            }
            contentModelArrayList[position].isExpanded=true
            notifyDataSetChanged()
        }

        /*holder.itemView.rootView.setOnClickListener {
            onItemClickListener.onClick(
                position, it, contentModelArrayList[position].id,
                contentModelArrayList[position].title, contentModelArrayList
            )

        }*/


    }


    fun updateData(mcontentModelArrayList: List<Data>) {
        contentModelArrayList = mcontentModelArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return contentModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvDescriptions: TextView
        var llTitle: LinearLayout
        var btnMyoderexpand: ImageView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDescriptions = itemView.findViewById(R.id.tvSubTitle)
            llTitle = itemView.findViewById(R.id.llTitle)
            btnMyoderexpand = itemView.findViewById(R.id.btnMyoderexpand)
        }
    }
}