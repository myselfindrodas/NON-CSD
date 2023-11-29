package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.categorymodel.Data

class HorizontalSubCategoryAdapter(ctx: Context, var onItemClickListener: HorizontalSubCategoryOnItemClickListener)
    :RecyclerView.Adapter<HorizontalSubCategoryAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    private var subCategoryArrayList: ArrayList<Data> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface HorizontalSubCategoryOnItemClickListener {
        fun onClick(position: Int, view: View, catId:String, catName:String)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalSubCategoryAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.horizontal_subcategory_list_item, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HorizontalSubCategoryAdapter.classViewHolder,
        position: Int
    ) {
        holder.tvHorizontalSubCategory.text = subCategoryArrayList[position].name

        holder.itemView.rootView.setOnClickListener {
            onItemClickListener.onClick(position, it,subCategoryArrayList[position].id,subCategoryArrayList[position].name)

        }
    }

    override fun getItemCount(): Int {
        return subCategoryArrayList.size
    }

    fun updateData(mCategoryArrayList: List<Data>){
        subCategoryArrayList= mCategoryArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvHorizontalSubCategory: TextView


        init {
            tvHorizontalSubCategory = itemView.findViewById(R.id.tvHorizontalSubCategory)

        }
    }

}