package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
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
import com.example.sainikgrocerycustomer.data.model.Category
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.application.GlideApp
import com.grocery.sainik_grocery.data.model.categorymodel.Data
import com.squareup.picasso.Picasso

class CategoryListAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoryListAdapter.MyViewHolder>() {
    private var imageURL: String="https://sainik.shyamfuture.in/"
    private val inflater: LayoutInflater
    private var productModelArrayList: ArrayList<Data> = arrayListOf()
    var ctx: Context
    private var expandedPosition = -1

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClickCategory(position: Int, view: View, mProductModelArrayList:Data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.category_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){

            tvCategoryItemname.text = productModelArrayList[position].name
            try {

                Picasso.get()
                    .load(imageURL+productModelArrayList[position].productCategoryUrl)
                    .error(R.drawable.login_img)
//                    .placeholder(R.drawable.loader_gif)
                    .into(holder.ivCategory)
            }catch (e:Exception){
                e.printStackTrace()
            }

            val isExpanded = position == expandedPosition

            if(isExpanded) {
                itemView.findViewById<View>(R.id.rvSubCategory).visibility = View.VISIBLE
                btnExpandCategory.setImageResource(R.drawable.ic_arrow_up)
            }
            else {
                itemView.findViewById<View>(R.id.rvSubCategory).visibility =  View.GONE
                btnExpandCategory.setImageResource(R.drawable.ic_arrow_down)
            }
            itemView.isActivated = isExpanded

            itemView.rootView.setOnClickListener {
//                onItemClickListener.onClickCategory(position, it, recyclerView = holder.rvSubCategory, productModelArrayList[position].id.toInt(),productModelArrayList[position].name)
//                expandedPosition = if(isExpanded) -1 else position
//                notifyDataSetChanged()

                onItemClickListener.onClickCategory(position, it, productModelArrayList[position])

            }

        }
        /*if (isCategory == true) {
            holder.lldiscount.visibility = View.GONE
            holder.tvItemprice.visibility = View.GONE
            holder.btnGo.visibility = View.GONE
            holder.tvItemname.gravity = Gravity.CENTER_HORIZONTAL
        } else {
            holder.lldiscount.visibility = View.VISIBLE
            holder.tvItemprice.visibility = View.VISIBLE
            holder.btnGo.visibility = View.VISIBLE
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(27, 0, 0, 0)
            holder.tvItemname.setLayoutParams(params)

        }*/
//        holder.lldiscount.visibility = View.GONE
//        holder.btnGo.visibility = View.GONE
//        holder.tvItemname.gravity = Gravity.CENTER_HORIZONTAL


//        holder.ivImg.setImageResource(productModelArrayList[position].image)
//        holder.tvItemprice.text = productModelArrayList[position].productprice

    }




    fun updateData(mProductModelArrayList: List<Data>){
        productModelArrayList= mProductModelArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return productModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCategoryItemname: TextView
        var ivCategory: ImageView
        var btnExpandCategory: ImageView
        var rvSubCategory: RecyclerView
//        var lldiscount: LinearLayout
//        var btnGo: AppCompatButton

        init {
            tvCategoryItemname = itemView.findViewById(R.id.tvCategoryItemname)
            ivCategory = itemView.findViewById(R.id.ivCategory)
            btnExpandCategory = itemView.findViewById(R.id.btnExpandCategory)
            rvSubCategory = itemView.findViewById(R.id.rvSubCategory)
//            lldiscount = itemView.findViewById(R.id.lldiscount)
//            btnGo = itemView.findViewById(R.id.btnGo)
        }
    }
}