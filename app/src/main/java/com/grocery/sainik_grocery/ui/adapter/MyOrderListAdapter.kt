package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.Order
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.orderlistmodel.Data
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MyOrderListAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MyOrderListAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var orderModelArrayList: ArrayList<Data> = arrayListOf()
    private var imageURL: String="https://sainik.shyamfuture.in/ProductImages/Thumbnail/"
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, mOrderData: Data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.myorder_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvItemname.text = orderModelArrayList[position].orderNumber
//            val streetDetails =
//                if (orderModelArrayList[position].streetDetails != null) orderModelArrayList[position].streetDetails else ""
//            val cityDetails =
//                if (orderModelArrayList[position].city != null) orderModelArrayList[position].city else ""
//            val areaDetails =
//                if (orderModelArrayList[position].area != null) orderModelArrayList[position].area else ""
            if (orderModelArrayList[position].deliveryAddress.isNullOrEmpty()){

                tvItemLocation.text = ""

            }else{
                tvItemLocation.text = orderModelArrayList[position].deliveryAddress

            }
            tvItemAmount.text = "Price: ₹ ${orderModelArrayList[position].totalAmount}"

            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//            val input2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

            val output = SimpleDateFormat("dd-MM-yyyy")
            var d: Date? = null
            try {
                d = input.parse(orderModelArrayList[position].soCreatedDate.toString())
                val formatted: String = output.format(d)
                holder.tvOrderDate.text = formatted
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            when (orderModelArrayList[position].deliveryStatus) {
                1 -> {
                    holder.tvItemStatus.setTextColor(ContextCompat.getColor(ctx, R.color.blue));
                    holder.tvItemStatus.compoundDrawablePadding = 10;
                    holder.tvItemStatus.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_circle_yellow,
                        0,
                        0,
                        0
                    );
                }
                2 -> {
                    holder.tvItemStatus.setTextColor(ContextCompat.getColor(ctx, R.color.orange));
                    holder.tvItemStatus.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_circle_yellow,
                        0,
                        0,
                        0
                    );
                    holder.tvItemStatus.compoundDrawablePadding = 10;
                }
                3 -> {
                    holder.tvItemStatus.setTextColor(ContextCompat.getColor(ctx, R.color.green));
                    holder.tvItemStatus.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_check_circle_green,
                        0,
                        0,
                        0
                    );
                    holder.tvItemStatus.compoundDrawablePadding = 10;

                }
                4 -> {
                    holder.tvItemStatus.setTextColor(ContextCompat.getColor(ctx, R.color.orange));
                    holder.tvItemStatus.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_circle_yellow,
                        0,
                        0,
                        0
                    );
                    holder.tvItemStatus.compoundDrawablePadding = 10;

                }
                5 -> {
                    holder.tvItemStatus.setTextColor(ContextCompat.getColor(ctx, R.color.red));
                    holder.tvItemStatus.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_circle_red,
                        0,
                        0,
                        0
                    );
                    holder.tvItemStatus.compoundDrawablePadding = 10;

                }
                else -> {
                    holder.tvItemStatus.setTextColor(ContextCompat.getColor(ctx, R.color.orange));
                    holder.tvItemStatus.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_circle_yellow,
                        0,
                        0,
                        0
                    );
                    holder.tvItemStatus.compoundDrawablePadding = 10;

                }
            }

            holder.tvItemStatus.text = when (orderModelArrayList[position].deliveryStatus) {
                1 -> {
                    "Order Placed"
                }
                2 -> {
                    "Item Processed"
                }
                3 -> {
                    "Delivered"
                }
                4 -> {
                    "Shipping Delivered"
                }
                5 -> {
                    "Order Canceled"
                }
                else -> {
                    "On process"
                }
            }
            try {

                Picasso.get()
                    .load(R.drawable.login_img)
                    .error(R.drawable.login_img)
//                    .placeholder(R.drawable.loader_gif)
                    .into(holder.ivImg)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            // holder.ivImg.setImageResource(productModelArrayList[position].productid)
            itemView.rootView.setOnClickListener {
                onItemClickListener.onClick(position, it, orderModelArrayList[position])

            }

        }
    }


    fun updateData(mOrderModelArrayList: List<Data>) {
        orderModelArrayList = mOrderModelArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orderModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItemname: TextView

        var ivImg: ImageView
        var tvItemStatus: TextView
        var tvOrderDate: TextView
        var tvItemAmount: TextView
        var tvItemLocation: TextView

        init {
            ivImg = itemView.findViewById(R.id.ivItemImg)
            tvItemname = itemView.findViewById(R.id.tvItemname)
            tvItemStatus = itemView.findViewById(R.id.tvItemStatus)
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate)
            tvItemAmount = itemView.findViewById(R.id.tvItemAmount)
            tvItemLocation = itemView.findViewById(R.id.tvItemLocation)
        }
    }
}