package com.grocery.sainik_grocery.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.productmaincategorymodel.Data


class ChooseMainCategoryAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ChooseMainCategoryAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var maincategotyModelArrayList: ArrayList<Data> = arrayListOf()
    private var imageURL: String="https://maitricomplex.in/"
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(position: Int, view: View, mMaincategotyModelArrayList: ArrayList<Data>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_choosefavourites, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvCatname.text = maincategotyModelArrayList[position].name

            itemView.setOnClickListener {
                onItemClickListener.onClick(position, it, maincategotyModelArrayList)

            }


            Glide.with(ctx)
                .load(imageURL+maincategotyModelArrayList[position].categoryImageUrl)
                .timeout(6000)
                .error(R.drawable.bgbanner)
//            .placeholder(R.drawable.adbg1)
                .into(holder.rvItemImg)



        }
    }

    fun updateData(mMaincategotyModelArrayList: List<Data>) {
        maincategotyModelArrayList = mMaincategotyModelArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return maincategotyModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvCatname: TextView
        var rvItemImg: ImageView


        init {

            tvCatname = itemView.findViewById(R.id.tvCatname)
            rvItemImg = itemView.findViewById(R.id.rvItemImg)

        }
    }
}