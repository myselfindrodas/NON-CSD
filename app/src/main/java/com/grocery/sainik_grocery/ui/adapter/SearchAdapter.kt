package com.grocery.sainik_grocery.ui.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.productlistmodel.Data
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SearchAdapter(val context: Context, var onItemClickListener: SearchItemClickListener)
    :RecyclerView.Adapter<SearchAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var searchList: ArrayList<Data> = ArrayList()
    private var imageURL: String="https://maitricomplex.in/"

    init {
        inflater = LayoutInflater.from(context)
    }

    interface SearchItemClickListener {
        fun searchListOnClick(position: Int, list: ArrayList<Data>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.search_list2, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.classViewHolder, position: Int) {

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Date()
        Log.d(TAG,"currentTime-->"+sdf.format(currentTime))

        with(holder){

            if (searchList[position].stock==null || searchList[position].stock.toInt()<=0){
                holder.itemView.setOnClickListener {
                    Toast.makeText(context,"Product Out of stock", Toast.LENGTH_SHORT).show()

                }

            }else{


                if (searchList[position].isProductOrderTime){

                    if (sdf.format(currentTime).toString()>= searchList[position].orderStartTime.toString()
                        && sdf.format(currentTime).toString()<= searchList[position].orderEndTime.toString()){
                        holder.itemView.setOnClickListener {
                            onItemClickListener.searchListOnClick(position, searchList, it)
                            Log.d(TAG, "value-->"+(searchList[position].stock))

                        }
                    }else{
                        holder.itemView.setOnClickListener {
                            Toast.makeText(context,"Product Not Availabe at this time", Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{

                    holder.itemView.setOnClickListener {
                        onItemClickListener.searchListOnClick(position, searchList, it)
                        Log.d(TAG, "value-->"+(searchList[position].stock))

                    }
                }

//                holder.itemView.setOnClickListener {
//                    onItemClickListener.searchListOnClick(position, searchList, it)
//                    Log.d(TAG, "value-->"+(searchList[position].stock))
//
//                }

            }







            if (searchList[position].name.isNullOrEmpty()) {
                searchText.text = " "
            } else {
                searchText.text = searchList[position].name
            }

            if (position==searchList.size-1){
                line.visibility = View.GONE
            }else{
                line.visibility = View.VISIBLE
            }

            Picasso.get()
                .load(imageURL+searchList[position].productUrl)
                .error(R.drawable.noimagefound)
                .into(holder.imgProduct)




//            itemView.setOnClickListener {
//
//
//                onItemClickListener.searchListOnClick(position, searchList, it)
//
//            }
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    fun updateData( mSearchList: List<Data>) {
        searchList = mSearchList as ArrayList<Data>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var searchText: TextView
        var imgProduct: ImageView
        var line: View
        init {
            searchText = itemView.findViewById(R.id.searchText)
            imgProduct = itemView.findViewById(R.id.imgProduct)
            line = itemView.findViewById(R.id.line)
        }
    }
}