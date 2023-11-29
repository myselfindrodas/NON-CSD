package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.ProductPackSize
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.utils.Utilities


class PackSizeAdapter(
    ctx: Context,
    var onItemClickListener: OnPackItemClickListener
) :
    RecyclerView.Adapter<PackSizeAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var packSizelArrayList: ArrayList<ProductPackSize> = arrayListOf()
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
        val view = inflater.inflate(R.layout.pack_size_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvPriceOld.text = "₹ ${ packSizelArrayList[position].price }"
            tvPrice.text = "₹ ${ packSizelArrayList[position].sellingPrice}"

            tvPriceOld.paintFlags = tvPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            // holder.ivImg.setImageResource(productModelArrayList[position].productid)
            tvQty.text = packSizelArrayList[position].size

            if (packSizelArrayList[position].price>packSizelArrayList[position].sellingPrice){
                tvPriceOld.visibility = View.VISIBLE
            }else{
                tvPriceOld.visibility = View.GONE
            }
           // packSizelArrayList[position].isSelected = packSizelArrayList[position].is_primary==1
            rbSelected.isChecked=packSizelArrayList[position].is_primary==1

            llitem.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                packSizelArrayList.forEach {item->
                    item.is_primary=0
                }
                onItemClickListener.onPackClick(position, it)

                packSizelArrayList[position].is_primary=1
                notifyDataSetChanged()
            }


        }
    }

    fun updateData(mPackSizeArrayList: List<ProductPackSize>){
        packSizelArrayList=mPackSizeArrayList as ArrayList<ProductPackSize>
        notifyDataSetChanged()
    }

    fun getList():ArrayList<ProductPackSize> = packSizelArrayList
    override fun getItemCount(): Int {
        return packSizelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvQty: TextView
        var tvPrice: TextView
        var tvPriceOld: TextView
        var llitem: LinearLayout
        var rbSelected: RadioButton
        var count = 0

        init {

            tvPrice = itemView.findViewById(R.id.tvPrice)
            tvPriceOld = itemView.findViewById(R.id.tvPriceOld)

            rbSelected = itemView.findViewById(R.id.rbSelected)
            llitem = itemView.findViewById(R.id.llitem)

            tvQty = itemView.findViewById(R.id.tvQty)

        }
    }
}