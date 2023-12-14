package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartData


class CartCountAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CartCountAdapter.MyViewHolder>() {
    private var imageURL: String="https://maitricomplex.in/ProductImages/"
    private val inflater: LayoutInflater
    private var cartModelArrayList: ArrayList<CartData> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)

        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, clickType: Int, cartData: CartData, count: Int, textView: TextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_orderitems, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvOrderitemname.text = convertToCamelCase(cartModelArrayList[position].productName)
            // holder.ivImg.setImageResource(productModelArrayList[position].productid)
            tvOrderitemqty.text = cartModelArrayList[position].quantity.toString()+" Item"
            tvPrice.text = "₹ "+cartModelArrayList[position].unitPrice


        }
    }


    fun updateData(mCartModelArrayList: List<CartData>) {
        cartModelArrayList = mCartModelArrayList as ArrayList<CartData>
        notifyDataSetChanged()
    }


    fun convertToCamelCase(input: String): String {
        val words = input.split(" ").toMutableList()
//        if (words.size == 1) {
//            return words[0].toLowerCase()
//        }

        for (i in 1 until words.size) {
            words[i] = words[i].replaceFirstChar { char -> char.uppercase() }
        }

        return words.joinToString(" ").toLowerCase().replaceFirstChar { char -> char.uppercase() }
    }

    override fun getItemCount(): Int {
        return cartModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvOrderitemname: TextView
        var tvOrderitemqty: TextView
        var tvPrice: TextView

        init {
            tvOrderitemname = itemView.findViewById(R.id.tvOrderitemname)
            tvOrderitemqty = itemView.findViewById(R.id.tvOrderitemqty)
            tvPrice = itemView.findViewById(R.id.tvPrice)

        }
    }
}