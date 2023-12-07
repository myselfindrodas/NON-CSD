package com.grocery.sainik_grocery.ui.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartData
import com.grocery.sainik_grocery.data.model.productlistmodel.Data
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    private var imageURL: String="https://sainik.shyamfuture.in/"
    private val inflater: LayoutInflater
    private var productModelArrayList: ArrayList<Data> = arrayListOf()
    private var cartModelArrayList: ArrayList<CartData> = arrayListOf()
    private lateinit var qtyList: Array<String>
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, tvQty: TextView, tvPrice: TextView, str: String, id: String, prodcutlist:Data)
        fun onUpdate(position: Int, view: View, count: Int, tvPrice: TextView, id: String, cartid:String, prodcutlist:Data, type:String, clicktype:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.product_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Date()
        Log.d(TAG,"currentTime-->"+sdf.format(currentTime))

        with(holder){

            holder.lldiscount.visibility = View.VISIBLE
            holder.tvPrice.visibility = View.VISIBLE
//        holder.btnGo.visibility = View.VISIBLE
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(25, 0, 0, 0)
            holder.tvItemname.setLayoutParams(params)

            if (productModelArrayList[position].isProductOrderTime){
                holder.tvAvailable.visibility = View.VISIBLE
                holder.tvAvailable.text = "Buy Time : "+productModelArrayList[position].orderStartTime+"-"+
                        productModelArrayList[position].orderEndTime






                if (sdf.format(currentTime).toString()>= productModelArrayList[position].orderStartTime.toString()
                    && sdf.format(currentTime).toString()<= productModelArrayList[position].orderEndTime.toString()){
                    holder.btnGo.visibility = View.VISIBLE
                    holder.llcounter.visibility = View.GONE
                    holder.itemView.setOnClickListener {
                        onItemClickListener.onClick(position, it, holder.tvQty, holder.tvPrice, "ivImg", productModelArrayList[position].id, productModelArrayList[position])

                    }
                }else{
                    holder.btnGo.visibility = View.GONE
                    holder.llcounter.visibility = View.GONE
                    holder.tvAvailable.visibility = View.VISIBLE
                    holder.tvAvailable.text = "ORDER TIME EXPRIED"
                    holder.itemView.setOnClickListener {
                        Toast.makeText(ctx,"Product Not Availabe at this time",Toast.LENGTH_SHORT).show()

                    }
                }

            }else{
                holder.tvAvailable.visibility = View.GONE
                holder.itemView.setOnClickListener {
                    onItemClickListener.onClick(position, it, holder.tvQty, holder.tvPrice, "ivImg", productModelArrayList[position].id, productModelArrayList[position])

                }

            }



            holder.tvCounterProduct.text = count.toString()




            holder.btnGo.setOnClickListener {
                if (productModelArrayList[position].stock.toInt()==count){
                    Toast.makeText(ctx,"Product Out of stock",Toast.LENGTH_SHORT).show();
                    return@setOnClickListener
                }
                holder.btnGo.visibility = View.GONE
                holder.llcounter.visibility = View.VISIBLE
                count++
                holder.tvCounterProduct.text = count.toString()
                onItemClickListener.onClick(position, it, holder.tvQty, holder.tvPrice, "addtocart", productModelArrayList[position].id, productModelArrayList[position])

            }



            holder.btnSub.setOnClickListener {

                if (count >= 1) {
                    count--
                    holder.tvCounterProduct.text = count.toString()
                    onItemClickListener.onUpdate(
                        position,
                        it,
                        count,
                        holder.tvPrice,
                        productModelArrayList[position].id,
                        cartid,
                        productModelArrayList[position],
                        "update",
                        0
                    )

                }else{

                    count = 0
                    holder.tvCounterProduct.text = count.toString()
                    holder.btnGo.visibility = View.VISIBLE
                    holder.llcounter.visibility = View.GONE

                }

                if (holder.tvCounterProduct.text.toString().equals("0")){
                    holder.btnGo.visibility = View.VISIBLE
                    holder.llcounter.visibility = View.GONE
                }
                /*tvCounter.text = count.toString()*/
            }

            holder.btnAdd.setOnClickListener {
                if (productModelArrayList[position].stock.toInt()==count){
                    Toast.makeText(ctx,"Product Out of stock",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                count++
                holder.tvCounterProduct.text = count.toString()
                onItemClickListener.onUpdate(position, it, count, holder.tvPrice,  productModelArrayList[position].id, cartid, productModelArrayList[position], "update", 1)

                /*tvCounter.text = count.toString()*/
            }



            try {

                Picasso.get()
                    .load(imageURL+productModelArrayList[position].productUrl)
                    .error(R.drawable.login_img)
                    .into(holder.ivImg)

            }catch (e:Exception){
                e.printStackTrace()
            }


            holder.tvItemname.text = convertToCamelCase(productModelArrayList[position].name?:"")


            if (productModelArrayList[position].discount?.toInt()!! > 0) {
                holder.tvDiscount.text = "${productModelArrayList[position].discount}% Off"
                holder.lldiscount.visibility = View.VISIBLE
            } else {
                holder.lldiscount.visibility = View.GONE
            }

            holder.tvPrice.text = " ₹ ${productModelArrayList[position].salesPrice.toString()}/${productModelArrayList[position].unitName}"


            holder.tvPriceOld.paintFlags = holder.tvPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


            holder.tvPriceOld.visibility = View.GONE









            holder.tvQty.setOnClickListener {
                onItemClickListener.onClick(position, it, holder.tvQty, holder.tvPrice, "tvQty", productModelArrayList[position].id, productModelArrayList[position])
            }



            if (cartModelArrayList.isNotEmpty()) {
                for (i in 0 until cartModelArrayList.size) {
                    if (cartModelArrayList[i].productId == productModelArrayList[position].id) {
                        count = cartModelArrayList[i].quantity
                        cartid = cartModelArrayList[i].id
                        holder.tvCounterProduct.text = count.toString()
                        if (count>0){
                            holder.btnGo.visibility = View.GONE
                            holder.llcounter.visibility = View.VISIBLE
                        }
                        Log.d(TAG, "count-->" + count)
                        return

                    } else {
                        count = 0
                        cartid = ""
                        Log.d(TAG, "count-->" + count)
                        holder.tvCounterProduct.text = count.toString()
                        if (productModelArrayList[position].isProductOrderTime) {
                            if (sdf.format(currentTime)
                                    .toString() >= productModelArrayList[position].orderStartTime.toString()
                                && sdf.format(currentTime)
                                    .toString() <= productModelArrayList[position].orderEndTime.toString()) {

                                holder.btnGo.visibility = View.VISIBLE
                                holder.llcounter.visibility = View.GONE

                            } else {

                                holder.btnGo.visibility = View.GONE
                                holder.llcounter.visibility = View.GONE
                            }
                        }else{


                            holder.btnGo.visibility = View.VISIBLE
                            holder.llcounter.visibility = View.GONE
                        }


                    }

                }
            }else{
                count = 0
                cartid = ""
                holder.tvCounterProduct.text = count.toString()
            }
        }



    }


    fun updateData(mProductModelArrayList: List<Data>, mCartModelArrayList: List<CartData>) {
        productModelArrayList = mProductModelArrayList as ArrayList<Data>
        cartModelArrayList = mCartModelArrayList as ArrayList<CartData>
        notifyDataSetChanged()
    }

    fun addData(mProductModelArrayList: List<Data>, mCartModelArrayList: List<CartData>) {

        val lastIndex: Int = mProductModelArrayList.size
        productModelArrayList.addAll(mProductModelArrayList)
        cartModelArrayList.addAll(mCartModelArrayList)
        notifyItemRangeInserted(lastIndex, mProductModelArrayList.size)

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
        return productModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItemname: TextView
        var ivImg: ImageView
        var tvPrice: TextView
        var tvPriceOld: TextView
        var tvDiscount: TextView
        var tvAvailable: TextView
        var lldiscount: LinearLayout
        var btnAdd: AppCompatButton
        var btnSub: AppCompatButton
        var btnGo: AppCompatButton
        var tvQty: TextView
        var llcounter:LinearLayout
//        var llAddProduct: LinearLayout
//        var btnSubProduct: AppCompatButton
//        var btnAddProduct: AppCompatButton
        var tvCounterProduct: TextView

        var count=0
        var cartid = ""

        init {
            tvItemname = itemView.findViewById(R.id.tvItemname)
            ivImg = itemView.findViewById(R.id.ivImg)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            tvPriceOld = itemView.findViewById(R.id.tvPriceOld)
            tvDiscount = itemView.findViewById(R.id.tvDiscount)
            tvAvailable = itemView.findViewById(R.id.tvAvailable)
            lldiscount = itemView.findViewById(R.id.lldiscount)
            btnGo = itemView.findViewById(R.id.btnGo)
            tvQty = itemView.findViewById(R.id.tvQty)
            llcounter = itemView.findViewById(R.id.llcounter)
//            llAddProduct = itemView.findViewById(R.id.llAddProduct)
            btnSub = itemView.findViewById(R.id.btnSub)
            btnAdd = itemView.findViewById(R.id.btnAdd)
            tvCounterProduct = itemView.findViewById(R.id.tvCounter)
            tvCounterProduct.text = count.toString()
        }
    }
}