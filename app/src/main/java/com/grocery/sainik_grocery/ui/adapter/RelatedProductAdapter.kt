package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.productlistmodel.Data
import com.squareup.picasso.Picasso

class RelatedProductAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RelatedProductAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var topsellingModelArrayList: ArrayList<Data> = arrayListOf()
    var ctx: Context
    private var imageURL: String="https://maitricomplex.in/"

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, id:String)
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

        holder.tvItemname.text = convertToCamelCase(topsellingModelArrayList[position].name?:"")
        try {
            Picasso.get()
                .load(imageURL+topsellingModelArrayList[position].productUrl)
                .error(R.drawable.noimagefound)
//                .placeholder(R.drawable.loader_gif)
                .into(holder.ivImg)

        }catch (e:Exception){
            e.printStackTrace()
        }

//        holder.ivImg.setImageResource(productModelArrayList[position].image)

        holder.tvPrice.text =
            " ₹ ${topsellingModelArrayList[position].salesPrice}/${topsellingModelArrayList[position].unitName?:""}"
        holder.tvPriceOld.text =
            "₹ ${topsellingModelArrayList[position].salesPrice}/${topsellingModelArrayList[position].unitName?:""}"

        holder.tvPriceOld.paintFlags = holder.tvPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

       /* if (topsellingModelArrayList[position].product.price>topsellingModelArrayList[position].sellingPrice){
            holder.tvPriceOld.visibility = View.VISIBLE
        }else{*/
            holder.tvPriceOld.visibility = View.GONE
       // }
        if (topsellingModelArrayList[position].discount!!>0) {
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




    fun updateData(mTopseelingModelArrayList: List<Data>){
        topsellingModelArrayList= mTopseelingModelArrayList as ArrayList<Data>
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