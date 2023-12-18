package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.squareup.picasso.Picasso

class SlidingBannerAdapter(val context: Context, var onItemClickListener: OnItemClickListener)
    :RecyclerView.Adapter<SlidingBannerAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var bannerImages: ArrayList<Int> = arrayListOf()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SlidingBannerAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.sliding_banner, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlidingBannerAdapter.classViewHolder, position: Int) {
        with(holder) {

            Picasso.get()
                .load(bannerImages[position])
                .error(R.drawable.bgbanner)
//                .placeholder(R.drawable.loader_gif)
                .into(ivBanner)


            holder.itemView.setOnClickListener {

                val bundle = Bundle()
                bundle.putString("viewalltype", "category")
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_productlist, bundle)

            }

        }
    }

    override fun getItemCount(): Int {
        return bannerImages.size
    }

    fun updateBannerImage(mBannerImages: ArrayList<Int>) {
        bannerImages = mBannerImages
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivBanner: ImageView

        init {
            ivBanner = itemView.findViewById(R.id.ivBanner)
        }
    }

}