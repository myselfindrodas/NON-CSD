package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.Attribute
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.utils.Utilities


class FilterPackAdapter(
    ctx: Context,
    var onItemClickListener: OnPackItemClickListener
) :
    RecyclerView.Adapter<FilterPackAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var packSizelArrayList: ArrayList<Attribute> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
       // this.savedCardModelArrayList = savedCardModelArrayList
        this.ctx = ctx
    }

    interface OnPackItemClickListener {
        fun onPackClick(position: Int, view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.filter_pack_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvQty.text = packSizelArrayList[position].name

           // packSizelArrayList[position].isSelected = packSizelArrayList[position].is_primary==1
            cbSelected.isChecked=packSizelArrayList[position].isChecked==true

            llitem.setOnClickListener {
               /* packSizelArrayList.forEach {item->
                    item.isChecked=false
                }*/
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                onItemClickListener.onPackClick(position, it)


                packSizelArrayList[position].isChecked= !packSizelArrayList[position].isChecked
                notifyDataSetChanged()
            }


        }
    }

    fun updateData(mPackSizeArrayList: List<Attribute>){
        packSizelArrayList=mPackSizeArrayList as ArrayList<Attribute>
        notifyDataSetChanged()
    }

    fun getList():ArrayList<Attribute> = packSizelArrayList
    override fun getItemCount(): Int {
        return packSizelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvQty: TextView
        var llitem: LinearLayout
        var cbSelected: CheckBox
        var count = 0

        init {


            cbSelected = itemView.findViewById(R.id.cbSelected)
            llitem = itemView.findViewById(R.id.llitem)

            tvQty = itemView.findViewById(R.id.tvQty)

        }
    }
}