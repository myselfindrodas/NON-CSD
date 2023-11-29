package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.orderdetailsmodel.SalesOrderItem
import com.squareup.picasso.Picasso


class ReturnItemAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ReturnItemAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var itemModelArrayList: ArrayList<SalesOrderItem> = arrayListOf()
    var ctx: Context

    //    private var imageURL: String="https://sainik.shyamfuture.in/ProductImages/Thumbnail/""https://sainik.shyamfuture.in/api/"
    private var imageURL: String = "https://sainik.shyamfuture.in/ProductImages/Thumbnail/"

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, id: String, note: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_return_orderitem, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvitemName.text = itemModelArrayList[position].productName
            tvitemSize.text = itemModelArrayList[position].unitConversation.name
            //tvitemSize.text = "12KG"
            Picasso.get()
                .load(imageURL + itemModelArrayList[position].product.productUrl)
                .error(R.drawable.login_img)
//                .placeholder(R.drawable.loader_gif)
                .into(holder.itemImg)
//            if (itemModelArrayList[position].isReturnNoteShown) {
//                llNote.visibility = View.VISIBLE
//                btnSubmit.visibility = View.VISIBLE
//                cbReturned.isChecked = true
//                ivDown.rotation = 270f
//            } else {
//                cbReturned.isChecked = false
//                llNote.visibility = View.GONE
//                btnSubmit.visibility = View.GONE
//                ivDown.rotation = 90f
//            }

            when (itemModelArrayList[position].status) {
                0 -> {
                    cbReturned.visibility = View.VISIBLE
                    tvOrderStatus.visibility = View.GONE

                }

                1 -> {
                    cbReturned.visibility = View.GONE
                    tvOrderStatus.visibility = View.VISIBLE
                    tvOrderStatus.text = "Return Requested"
                    tvOrderStatus.setTextColor(ContextCompat.getColor(ctx, R.color.orange));

                }

                2 -> {
                    cbReturned.visibility = View.GONE
                    tvOrderStatus.visibility = View.VISIBLE
                    tvOrderStatus.text = "Return Success"
                    tvOrderStatus.setTextColor(ContextCompat.getColor(ctx, R.color.green));

                }
            }

            /*cbReturned.setOnCheckedChangeListener { buttonView, isChecked ->

                itemModelArrayList.forEach { item ->
                    item.isReturnNoteShown = false
                }
                itemModelArrayList[position].isReturnNoteShown = isChecked

                //notifyDataSetChanged()

                  onItemClickListener.onClick(position,cbReturned)

            }*/

            itemView.setOnClickListener {

//                if (itemModelArrayList[position].order_status>0){
//                    Toast.makeText(ctx, "This order has already been returned", Toast.LENGTH_SHORT)
//                        .show()
//                    return@setOnClickListener
//                }
//                itemModelArrayList.forEach { item ->
//                    item.isReturnNoteShown = false
//                }
//                itemModelArrayList[position].isReturnNoteShown = true

                notifyDataSetChanged()
                //  onItemClickListener.onClick(position,it)

            }

            btnSubmit.setOnClickListener {
                if (cbReturned.isChecked) {
                    onItemClickListener.onClick(
                        position,
                        it,
                        itemModelArrayList[position].id,
                        editTextbugDescription.text.toString().trim()
                    )
                } else {
                    Toast.makeText(ctx, "Check the item to be returned.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun updateData(mitemModelArrayList: List<SalesOrderItem>) {
        itemModelArrayList = mitemModelArrayList as ArrayList<SalesOrderItem>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvitemName: TextView
        var tvitemSize: TextView
        var tvOrderStatus: TextView
        var ivDown: ImageView
        var itemImg: ImageView
        var llNote: LinearLayout
        var llMain: LinearLayout
        var editTextbugDescription: AppCompatEditText
        var btnSubmit: AppCompatButton
        var cbReturned: CheckBox


        init {

            tvitemName = itemView.findViewById(R.id.tvitemName)
            tvitemSize = itemView.findViewById(R.id.tvitemSize)
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus)
            ivDown = itemView.findViewById(R.id.ivDown)
            itemImg = itemView.findViewById(R.id.itemImg)
            llNote = itemView.findViewById(R.id.llNote)
            llMain = itemView.findViewById(R.id.llMain)
            editTextbugDescription = itemView.findViewById(R.id.editTextbugDescription)
            btnSubmit = itemView.findViewById(R.id.btnSubmit)
            cbReturned = itemView.findViewById(R.id.cbReturned)
        }
    }
}