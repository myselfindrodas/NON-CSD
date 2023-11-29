package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sainikgrocerycustomer.data.model.ProductImage
import com.grocery.sainik_grocery.R
import com.squareup.picasso.Picasso


class ProductItemAdapter(
    ctx: Context,
    var onItemClickListener: OnImageItemClickListener
) :
    RecyclerView.Adapter<ProductItemAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var ProductImageArrayList: ArrayList<ProductImage> = arrayListOf()
    private var imageURL:String=""
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
       // this.savedCardModelArrayList = savedCardModelArrayList
        this.ctx = ctx
    }

    interface OnImageItemClickListener {
        fun onImgItemClick(position: Int, view: View, imgUrl:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.product_details_img_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            /*.timeout(3000)
            .error(R.drawable.item)*/
            try {
                Picasso.get()
                    .load(imageURL+ProductImageArrayList[position].image)
                    .error(R.drawable.login_img)
//                    .placeholder(R.drawable.loader_gif)
                    .into(holder.ivItemImg)

            }catch (e:Exception){
                e.printStackTrace()
            }
            if (ProductImageArrayList[position].isSelected){
                llitem.setBackgroundResource(R.drawable.rounded_orange_border)
            }else{
                llitem.setBackgroundResource(R.drawable.grey_rounded_border)
            }

            itemView.rootView.setOnClickListener {
                ProductImageArrayList.forEach {item->
                item.isSelected=false
                }
                ProductImageArrayList[position].isSelected=true
                onItemClickListener.onImgItemClick(position, it, imageURL+"/"+ProductImageArrayList[position].image)
                notifyDataSetChanged()

            }


        }
    }

    fun updateData(mProductImageModelArrayList: List<ProductImage>, mImageURL:String){
        imageURL=mImageURL
        ProductImageArrayList=mProductImageModelArrayList as ArrayList<ProductImage>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return ProductImageArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivItemImg: ImageView
        var llitem: LinearLayout

        init {

            ivItemImg = itemView.findViewById(R.id.ivImg)
            llitem = itemView.findViewById(R.id.llitem)

        }
    }
}