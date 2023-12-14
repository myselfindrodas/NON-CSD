package com.grocery.sainik_grocery.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.Addres
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.model.addresslistmodel.Data


class AddressAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var addressModelArrayList: ArrayList<Data> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(position: Int, view: View, mAddressModelArrayList: ArrayList<Data>, isDelete:Boolean=false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_address, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvaddressTitle.text = addressModelArrayList[position].type
            tvaddressfull.text =
                addressModelArrayList[position].type + " " + addressModelArrayList[position].houseNo +
                        "\n" + addressModelArrayList[position].streetDetails +"\n" +
                        addressModelArrayList[position].landMark

            rbCard.isChecked = addressModelArrayList[position].isPrimary == true

            itemView.rootView.setOnClickListener {
                onItemClickListener.onClick(position, it,addressModelArrayList,false)

            }

            btnDelete.setOnClickListener {

                onItemClickListener.onClick(position, it,addressModelArrayList,true)
            }

            btnhomeAddaddress.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("viewtype", "edit")
                bundle.putSerializable("data", addressModelArrayList[position]!!)

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_addaddress, bundle)
            }




        }
    }

    fun updateData(mAddressModelArrayList: List<Data>) {
        addressModelArrayList = mAddressModelArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return addressModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvaddressTitle: TextView
        var tvaddressfull: TextView
        var rbCard: RadioButton
        var btnhomeAddaddress: ImageView
        var btnDelete: ImageView
        var count = 0
        var rbHome:RadioButton

        init {

            tvaddressTitle = itemView.findViewById(R.id.tvaddressTitle)
            rbCard = itemView.findViewById(R.id.rbHome)
            tvaddressfull = itemView.findViewById(R.id.tvaddressfull)
            btnhomeAddaddress = itemView.findViewById(R.id.btnhomeAddaddress)
            btnDelete = itemView.findViewById(R.id.btnDelete)
            rbHome = itemView.findViewById(R.id.rbHome)
        }
    }
}