package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.PaymentCard
import com.grocery.sainik_grocery.R


class SavedCardAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<SavedCardAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var savedCardModelArrayList: ArrayList<PaymentCard> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
       // this.savedCardModelArrayList = savedCardModelArrayList
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, mSavedCardModelArrayList: ArrayList<PaymentCard>, isDelete:Boolean=false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.saved_cards_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvCardname.text = savedCardModelArrayList[position].holderName
            // holder.ivImg.setImageResource(productModelArrayList[position].productid)
            tvCardDate.text = "Exp.Date: "+savedCardModelArrayList[position].expDate
            tvCardNoTxt.text = savedCardModelArrayList[position].cardNumber

            rbCard.isChecked= savedCardModelArrayList[position].isPrimary == 1

            itemView.rootView.setOnClickListener {
                onItemClickListener.onClick(position, it,savedCardModelArrayList, false)

            }

            btnDeletecard.setOnClickListener {

                onItemClickListener.onClick(position, it,savedCardModelArrayList,true)
            }


        }
    }

    fun updateData(mSavedCardModelArrayList: List<PaymentCard>){
        savedCardModelArrayList=mSavedCardModelArrayList  as ArrayList<PaymentCard>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return savedCardModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvCardname: TextView
        var ivItemImg: ImageView
        var tvCardDate: TextView
        var tvCardNoTxt: TextView
        var btnDeletecard: ImageView
        var rbCard: RadioButton
        var count = 0

        init {

            tvCardNoTxt = itemView.findViewById(R.id.tvCardNoTxt)
            rbCard = itemView.findViewById(R.id.rbCard)
            ivItemImg = itemView.findViewById(R.id.ivItemImg)
            tvCardname = itemView.findViewById(R.id.tvCardname)
            tvCardDate = itemView.findViewById(R.id.tvCardDate)
            btnDeletecard = itemView.findViewById(R.id.btnDeletecard)
        }
    }
}