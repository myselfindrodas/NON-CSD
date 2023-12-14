package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.loginbannermodel.Data

class LoginBannerAdapter(var context: Context) :
    RecyclerView.Adapter<LoginBannerAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    var modelList:  ArrayList<Data> = arrayListOf()
    private var imageURL: String="https://maitricomplex.in/"
    var ctx: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = inflater.inflate(R.layout.sliding_banner2, parent, false)
        return MyViewHolder(view)
    }


    fun updateList(mModelList: List<Data>) {
        modelList= mModelList as ArrayList<Data>
        notifyDataSetChanged()

    }

    fun addToList(mModelList: List<Data>) {
        val lastIndex: Int = modelList.size
        modelList.addAll(mModelList)
        notifyItemRangeInserted(lastIndex, mModelList.size)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context)
            .load(imageURL+modelList[position].imageUrl)
            .timeout(6000)
            .error(R.drawable.login_img)
//            .placeholder(R.drawable.adbg1)
            .into(holder.ivBanner)



    }

    override fun getItemCount(): Int {
        return modelList.size
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivBanner: ImageView

        init {
            ivBanner = itemView.findViewById(R.id.ivBanner)

        }
    }

    init {
        inflater = LayoutInflater.from(context)
        this.ctx = context
    }
}