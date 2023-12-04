package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartData
import com.squareup.picasso.Picasso


class CartCountAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CartCountAdapter.MyViewHolder>() {
    private var imageURL: String="https://sainik.shyamfuture.in/ProductImages/"
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

            tvOrderitemname.text = cartModelArrayList[position].productName
            // holder.ivImg.setImageResource(productModelArrayList[position].productid)
            tvOrderitemqty.text = cartModelArrayList[position].quantity.toString()+" Item"


        }
    }


    fun updateData(mCartModelArrayList: List<CartData>) {
        cartModelArrayList = mCartModelArrayList as ArrayList<CartData>
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return cartModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvOrderitemname: TextView
        var tvOrderitemqty: TextView

        init {
            tvOrderitemname = itemView.findViewById(R.id.tvOrderitemname)
            tvOrderitemqty = itemView.findViewById(R.id.tvOrderitemqty)

        }
    }
}