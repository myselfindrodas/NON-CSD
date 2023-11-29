package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.Category
import com.grocery.sainik_grocery.R

class SubCategoryListAdapter (ctx: Context, var onItemClickListener: SubCategoryOnItemClickListener)
    :RecyclerView.Adapter<SubCategoryListAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    private var subCategoryArrayList: ArrayList<Category> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface SubCategoryOnItemClickListener {
        fun onClickSubCategory(position: Int, view: View, catId:Int, catName:String)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoryListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.subcategory_list_item, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubCategoryListAdapter.classViewHolder, position: Int) {
        holder.tvSubCategoryItemname.text = subCategoryArrayList[position].name

        holder.itemView.rootView.setOnClickListener {
            onItemClickListener.onClickSubCategory(position, it,subCategoryArrayList[position].categoryId,subCategoryArrayList[position].name)

        }
    }

    override fun getItemCount(): Int {
        return subCategoryArrayList.size
    }

    fun updateData(mCategoryArrayList: ArrayList<Category>){
        subCategoryArrayList= mCategoryArrayList
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvSubCategoryItemname: TextView


        init {
            tvSubCategoryItemname = itemView.findViewById(R.id.tvSubCategoryItemname)

        }
    }
}