package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.orderdetailsmodel.SalesOrderItem
import com.squareup.picasso.Picasso


class OrderDetailsListAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<OrderDetailsListAdapter.MyViewHolder>() {
    private var imageURL: String="https://sainik.shyamfuture.in/ProductImages/Thumbnail/"
    private val inflater: LayoutInflater
    private var orderHistoryModelArrayList: ArrayList<SalesOrderItem> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.order_details_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvItemname.text = convertToCamelCase(orderHistoryModelArrayList[position].productName)
            // holder.ivImg.setImageResource(productModelArrayList[position].productid)
            tvItemprice.text = "Price: ₹ " + orderHistoryModelArrayList[position].unitPrice.toString()
            tvItemAmtTxt.text = orderHistoryModelArrayList[position].unitConversation.name
            tvItemQtyTxt.text = "Qty: "+orderHistoryModelArrayList[position].quantity.toString()
            try {

                Picasso.get()
                    .load(imageURL+orderHistoryModelArrayList[position].product.productUrl)
                    .error(R.drawable.login_img)
//                    .placeholder(R.drawable.loader_gif)
                    .into(holder.ivImg)
            }catch (e:Exception){
                e.printStackTrace()
            }
            itemView.rootView.setOnClickListener {
                onItemClickListener.onClick(position, it)
            }

        }
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

    fun updateData(mOrderHistoryModelArrayList: List<SalesOrderItem>){
        orderHistoryModelArrayList = mOrderHistoryModelArrayList as ArrayList<SalesOrderItem>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orderHistoryModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItemname: TextView

         var ivImg: ImageView
        var tvItemprice: TextView
        var tvItemAmtTxt: TextView
        var tvItemQtyTxt: TextView

        init {
            tvItemname = itemView.findViewById(R.id.tvItemname)
            ivImg = itemView.findViewById(R.id.ivItemImg)
            tvItemprice = itemView.findViewById(R.id.tvItemprice)
            tvItemAmtTxt = itemView.findViewById(R.id.tvItemAmtTxt)
            tvItemQtyTxt = itemView.findViewById(R.id.tvItemQtyTxt)
        }
    }
}