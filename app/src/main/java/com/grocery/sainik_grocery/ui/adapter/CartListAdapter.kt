package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartData
import com.squareup.picasso.Picasso


class CartListAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CartListAdapter.MyViewHolder>() {
    private var imageURL: String="https://sainik.shyamfuture.in/ProductImages/"
    private val inflater: LayoutInflater
    private var cartModelArrayList: ArrayList<CartData> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)

        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, clickType: Int, cartData: CartData, count: Int, textView: TextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.cart_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvItemname.text = convertToCamelCase(cartModelArrayList[position].productName)
            // holder.ivImg.setImageResource(productModelArrayList[position].productid)
            tvItemprice.text = "₹ " + cartModelArrayList[position].unitPrice.toString()
            tvProductcategory.text = cartModelArrayList[position].product.productCategory.name
            /* itemView.rootView.setOnClickListener {
                 onItemClickListener.onClick(position, it)

             }*/

            try {
                Picasso.get()
                    .load(imageURL+cartModelArrayList[position].product.productUrl)
                    .error(R.drawable.login_img)
//                    .placeholder(R.drawable.loader_gif)
                    .into(holder.ivImg)
            }catch (e:Exception){
                e.printStackTrace()
            }
            count=cartModelArrayList[position].quantity
            tvCounter.text=count.toString()

            btnSub.setOnClickListener {
                if (count > 0)
                    count--
                onItemClickListener.onClick(position, it, 0,cartModelArrayList[position], count, tvCounter)

                /*tvCounter.text = count.toString()*/
            }

            btnAdd.setOnClickListener {
                if (cartModelArrayList[position].product.stock==count){
                    Toast.makeText(ctx,"Product Out of stock", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                count++
                onItemClickListener.onClick(position, it, 1, cartModelArrayList[position],count, tvCounter)

                /*tvCounter.text = count.toString()*/
            }

            ivDelete.setOnClickListener {

                onItemClickListener.onClick(position, it, 2, cartModelArrayList[position], count, tvCounter)
            }

        }
    }


    fun updateData(mCartModelArrayList: List<CartData>) {
        cartModelArrayList = mCartModelArrayList as ArrayList<CartData>
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
        return cartModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItemname: TextView
        var ivImg: ImageView
        var tvItemprice: TextView
        var tvCounter: TextView
        var tvProductcategory: TextView
        var ivDelete: ImageView
        var btnAdd: AppCompatButton
        var btnSub: AppCompatButton
        var count = 0

        init {
            btnAdd = itemView.findViewById(R.id.btnAdd)
            btnSub = itemView.findViewById(R.id.btnSub)
            tvCounter = itemView.findViewById(R.id.tvCounter)
            ivDelete = itemView.findViewById(R.id.ivDelete)
            ivImg = itemView.findViewById(R.id.ivItemImg)
            tvProductcategory = itemView.findViewById(R.id.tvProductcategory)
            tvCounter.text = count.toString()
            tvItemname = itemView.findViewById(R.id.tvItemname)
            tvItemprice = itemView.findViewById(R.id.tvItemprice)
        }
    }
}