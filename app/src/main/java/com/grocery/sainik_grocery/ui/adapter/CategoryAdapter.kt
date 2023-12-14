package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.categorymodel.Data
import com.squareup.picasso.Picasso

class CategoryAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    private var imageURL: String="https://maitricomplex.in/"
    private val inflater: LayoutInflater
    private var categoryModelArrayList: ArrayList<Data> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClickCategory(position: Int, view: View, catId:String, catName:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.cat_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.tvCatname.text = categoryModelArrayList[position].name
        try {
            Picasso.get()
                .load(imageURL+categoryModelArrayList[position].productCategoryUrl)
                .error(R.drawable.noimagefound)
//                .placeholder(R.drawable.loader_gif)
                .into(holder.imgIcon)

        }catch (e:Exception){
            e.printStackTrace()
        }

//        holder.ivImg.setImageResource(productModelArrayList[position].image)
        holder.itemView.rootView.setOnClickListener {
            onItemClickListener.onClickCategory(position, it, categoryModelArrayList[position].id, categoryModelArrayList[position].name)

        }
    }




    fun updateData(mCategoryModelArrayList: List<Data>){
        categoryModelArrayList= mCategoryModelArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return categoryModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCatname: TextView
        var imgIcon: ImageView

        init {
            tvCatname = itemView.findViewById(R.id.tvItemname)
            imgIcon = itemView.findViewById(R.id.ivImg)
        }
    }
}