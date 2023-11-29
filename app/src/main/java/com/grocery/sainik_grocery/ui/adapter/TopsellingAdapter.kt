package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sainikgrocerycustomer.data.model.DataProductList
import com.grocery.sainik_grocery.R
import com.squareup.picasso.Picasso

class TopsellingAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TopsellingAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var topsellingModelArrayList: ArrayList<DataProductList> = arrayListOf()
    var ctx: Context
    private var imageURL: String="https://sainik.shyamfuture.in/ProductImages/Thumbnail/"

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, id:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.product_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

       /* if (isCategory) {
            holder.lldiscount.visibility = View.GONE
            holder.tvItemprice.visibility = View.GONE
            holder.btnGo.visibility = View.GONE
            holder.tvItemname.gravity = Gravity.CENTER_HORIZONTAL
        } else {*/
            holder.lldiscount.visibility = View.VISIBLE
           // holder.tvItemprice.visibility = View.VISIBLE
            holder.btnGo.visibility = View.VISIBLE
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(27, 0, 0, 0)
      //  holder.tvItemname.layoutParams = params

      //  }

        holder.tvItemname.text = topsellingModelArrayList[position].name
        try {
            Picasso.get()
                .load(imageURL+topsellingModelArrayList[position].image)
                .error(R.drawable.login_img)
//                .placeholder(R.drawable.loader_gif)
                .into(holder.ivImg)

        }catch (e:Exception){
            e.printStackTrace()
        }

//        holder.ivImg.setImageResource(productModelArrayList[position].image)

        holder.tvPrice.text =
            " ₹ ${topsellingModelArrayList[position].sellingPrice}/${topsellingModelArrayList[position].units_of_measurement_types}"
        holder.tvPriceOld.text =
            "₹ ${topsellingModelArrayList[position].price}/${topsellingModelArrayList[position].units_of_measurement_types}"

        holder.tvPriceOld.paintFlags = holder.tvPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

       /* if (topsellingModelArrayList[position].product.price>topsellingModelArrayList[position].sellingPrice){
            holder.tvPriceOld.visibility = View.VISIBLE
        }else{*/
            holder.tvPriceOld.visibility = View.GONE
       // }
        if (topsellingModelArrayList[position].discount>0) {
            holder.tvDiscount.text = "${topsellingModelArrayList[position].discount}% Off"
            holder.lldiscount.visibility=View.VISIBLE
        }
        else {
            holder.lldiscount.visibility = View.GONE
        }

        holder.itemView.rootView.setOnClickListener {
            onItemClickListener.onClick(position, it,topsellingModelArrayList[position].id)

        }
    }




    fun updateData(mTopseelingModelArrayList: ArrayList<DataProductList>,mImageURL:String){
        topsellingModelArrayList= mTopseelingModelArrayList
        imageURL=mImageURL
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return topsellingModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItemname: TextView
        var ivImg: ImageView
        var tvPrice: TextView
        var tvPriceOld: TextView
        var tvDiscount: TextView
        var lldiscount: LinearLayout
        var btnGo: AppCompatButton

        init {
            tvItemname = itemView.findViewById(R.id.tvItemname)
            ivImg = itemView.findViewById(R.id.ivImg)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            tvPriceOld = itemView.findViewById(R.id.tvPriceOld)
            lldiscount = itemView.findViewById(R.id.lldiscount)
            tvDiscount = itemView.findViewById(R.id.tvDiscount)
            btnGo = itemView.findViewById(R.id.btnGo)
        }
    }
}