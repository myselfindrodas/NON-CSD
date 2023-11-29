package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.Urc
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.noncsdmodel.Data
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Utilities


class UrcListAdapter(
    ctx: Context,
    urcModelArrayList: ArrayList<Data>,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<UrcListAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var urcModelArrayList: ArrayList<Data>
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.urcModelArrayList = urcModelArrayList
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(position: Int, view: View,urcModelArrayList: ArrayList<Data> )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_urc, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvUrcName.text = urcModelArrayList[position].counterName.replaceFirstChar {char->
                char.uppercaseChar()
            }

            rbUrc.isChecked= urcModelArrayList[position].isSelected

            btnUrc.setOnClickListener {

                onItemClickListener.onClick(position, it, urcModelArrayList)
                Shared_Preferences.setUrcid(urcModelArrayList[position].id.toString())

            }



        }
    }

    fun updateData(mUrcModelArrayList: List<Data>){
        urcModelArrayList= mUrcModelArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }
    fun getURCList():List<Data> = urcModelArrayList

    override fun getItemCount(): Int {
        return urcModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvUrcName: TextView
        var rbUrc: RadioButton
        var btnUrc: LinearLayout
        var count = 0

        init {

            tvUrcName = itemView.findViewById(R.id.tvUrcName)
            rbUrc = itemView.findViewById(R.id.rbUrc)
            btnUrc = itemView.findViewById(R.id.btnUrc)
        }
    }
}