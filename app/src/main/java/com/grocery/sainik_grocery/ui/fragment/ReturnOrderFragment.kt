package com.grocery.sainik_grocery.ui.fragment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sainikgrocerycustomer.data.model.OrderHistory
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.orderdetailsmodel.OrderdetailsRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentReturnOrderBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.ReturnItemAdapter
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ReturnOrderFragment : Fragment(), ReturnItemAdapter.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    lateinit var fragmentReturnOrderBinding: FragmentReturnOrderBinding

    private var itemAdapter: ReturnItemAdapter? = null

    private var consignmentid: String = ""
    private var urc_id: Int = 0
    private lateinit var viewModel: CommonViewModel
    private var mDeliveryStatus = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentReturnOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_return_order, container, false)
        val root = fragmentReturnOrderBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm
        val intent = arguments
        if (intent != null && intent.containsKey("orderId")) {
            consignmentid = intent.getString("orderId", "")
        }
        println("orderId  $consignmentid")
        /*if (intent != null && intent.containsKey("urc_id")) {
            urc_id = intent.getInt("urc_id", 0)
        }*/

        return root
    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemAdapter =
            ReturnItemAdapter(mainActivity, this@ReturnOrderFragment)
        fragmentReturnOrderBinding.rvOrderitems.adapter = itemAdapter
        fragmentReturnOrderBinding.rvOrderitems.layoutManager = GridLayoutManager(mainActivity, 1)
//        val returnOrderList = ArrayList<OrderHistory>()
//        for (i in 1..5){
//            returnOrderList.add(OrderHistory("2023-04-11T06:02:53.000000Z","", 1, 4, 132.3, "shop", "", 2, "100gm", 2, 3, 1, 0, 44.2, "023-04-11T06:02:53.000000Z", 2))
//        }
//        itemAdapter!!.updateData(returnOrderList, "R.drawable.item")


        fragmentReturnOrderBinding.topnav.btnBack.setOnClickListener {

            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        getMyOrderDetails(consignmentid)

//        consignmentlist(consignmentid.toString())


    }



    private fun getMyOrderDetails(orderId:String) {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.orderdetails(OrderdetailsRequest(id= orderId, pageSize = 100, skip = 0))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {
                                        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                        val output = SimpleDateFormat("dd-MM-yyyy")
                                        var d: Date? = null
                                        try {
                                            d = input.parse(itResponse.data.soCreatedDate)
                                        } catch (e: ParseException) {
                                            e.printStackTrace()
                                        }
                                        val formatted: String = output.format(d!!)
                                        mDeliveryStatus = itResponse.data.deliveryStatus
                                        with(fragmentReturnOrderBinding) {

                                            when (itResponse.data.deliveryStatus) {

                                                1 -> {
                                                    btnpicked.visibility = android.view.View.VISIBLE
                                                    btnpicked.text = "Picked"
                                                    btnpicked.background =
                                                        ContextCompat.getDrawable(
                                                            mainActivity,
                                                            R.drawable.rectengel_orange
                                                        );

                                                    //  btnOrderprocess.visibility= android.view.View.GONE
                                                    //  btnNext.visibility= android.view.View.GONE
                                                    //  llimageupload.visibility = android.view.View.VISIBLE

                                                    /*Picasso.get()
                                                        .load(itProfileInfo.productImageUrl + "/" + itProfileInfo.data.order.pickupImage)
                                                        .error(R.drawable.orderitem)
                                                        .placeholder(R.drawable.orderitem)
                                                        .into(fragmentReturnOrderBinding.ivOrderdetailsimage)*/
                                                }
                                                2 -> {
                                                    btnpicked.visibility = android.view.View.VISIBLE
                                                    btnpicked.text = "Delivered"
                                                    btnpicked.background =
                                                        ContextCompat.getDrawable(
                                                            mainActivity,
                                                            R.drawable.rectengel_green
                                                        );

                                                    //  btnOrderprocess.visibility= android.view.View.GONE
                                                    //  btnNext.visibility= android.view.View.GONE
                                                    // llimageupload.visibility = android.view.View.VISIBLE

                                                    /*Picasso.get()
                                                        .load(itProfileInfo.orderImageUrl + "/" + itProfileInfo.data.order.pickupImage)
                                                        .error(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
                                                        .placeholder(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
                                                        .into(fragmentReturnOrderBinding.ivOrderdetailsimage)
                                                    Picasso.get()
                                                        .load(itProfileInfo.orderImageUrl + "/" + itProfileInfo.data.order.deliveryImage)
                                                        .error(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
                                                        .placeholder(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
                                                        .into(fragmentReturnOrderBinding.ivDeliverydetailsimage)*/
                                                }
                                                3 -> {
                                                    btnpicked.visibility = View.VISIBLE
                                                    btnpicked.text = "Pending"
                                                    btnpicked.background =
                                                        ContextCompat.getDrawable(
                                                            mainActivity,
                                                            R.drawable.rectengel_red
                                                        );

                                                    //  btnOrderprocess.visibility= android.view.View.GONE
                                                    // btnNext.visibility= android.view.View.VISIBLE
                                                    /* llimageupload.visibility = android.view.View.VISIBLE

                                                     Picasso.get()
                                                         .load(itProfileInfo.orderImageUrl + "/" + itProfileInfo.data.order.pickupImage)
                                                         .error(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
                                                         .placeholder(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
                                                         .into(fragmentReturnOrderBinding.ivOrderdetailsimage)*/
                                                }
                                                else -> {
                                                    btnpicked.visibility = android.view.View.GONE

                                                    // btnOrderprocess.visibility= android.view.View.VISIBLE
                                                    // btnNext.visibility= android.view.View.GONE
                                                    //  llimageupload.visibility = android.view.View.GONE
                                                }
                                            }
                                        }
                                        fragmentReturnOrderBinding.tvorderDate.text =
                                            "Order date : $formatted"
                                        fragmentReturnOrderBinding.tvordId.text =
                                            "Order ID : " + itResponse.data.id
                                        fragmentReturnOrderBinding.topnav.tvNavtitle.text =
                                            itResponse.data.orderNumber
                                        fragmentReturnOrderBinding.tvAmount.text =
                                            "Order Amount : ₹ " + itResponse.data.totalAmount
                                        if (itResponse.data.deliveryAddresses != null) {
                                            fragmentReturnOrderBinding.tvAddress.text = "Test"
                                        }
                                        itemAdapter?.updateData(itResponse.data.salesOrderItems)

//                                        if (itResponse.data.order == null || itResponse.data.order.orderHistory.isEmpty()) {
//
//                                            val navController =
//                                                Navigation.findNavController(fragmentReturnOrderBinding.root)
//                                            navController.navigate(R.id.nav_myorder)
//                                        }
                                    }
                                }


                            }
                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                val builder = AlertDialog.Builder(mainActivity)
                                builder.setMessage(it.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.show()
//                                fragmentOrderDetailsBinding.llDeliveryAddress.visibility=View.VISIBLE
//                                fragmentOrderDetailsBinding.clPriceDetails.visibility=View.VISIBLE
                            }

                            Status.LOADING -> {
                                mainActivity.showProgressDialog()
                            }

                        }

                    }
                }

        } else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()
        }

    }


    @SuppressLint("SetTextI18n")
//    private fun consignmentlist(id: String) {
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            viewModel.orderDetails(id = id).observe(mainActivity) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                             mainActivity.hideProgressDialog()
//                            resource.data?.let { itProfileInfo ->
//                                if (itProfileInfo.status) {
//                                    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                                    val output = SimpleDateFormat("dd-MM-yyyy")
//                                    var d: Date? = null
//                                    try {
//                                        d = input.parse(itProfileInfo.data.order.createdAt)
//                                    } catch (e: ParseException) {
//                                        e.printStackTrace()
//                                    }
//                                    val formatted: String = output.format(d!!)
//                                    mDeliveryStatus = itProfileInfo.data.order.delivery_status
//                                    with(fragmentReturnOrderBinding) {
//
//                                        when (itProfileInfo.data.order.delivery_status) {
//
//                                            1 -> {
//                                                btnpicked.visibility = android.view.View.VISIBLE
//                                                btnpicked.text = "Picked"
//                                                btnpicked.background =
//                                                    ContextCompat.getDrawable(
//                                                        mainActivity,
//                                                        R.drawable.rectengel_orange
//                                                    );
//
//                                                //  btnOrderprocess.visibility= android.view.View.GONE
//                                                //  btnNext.visibility= android.view.View.GONE
//                                                //  llimageupload.visibility = android.view.View.VISIBLE
//
//                                                /*Picasso.get()
//                                                    .load(itProfileInfo.productImageUrl + "/" + itProfileInfo.data.order.pickupImage)
//                                                    .error(R.drawable.orderitem)
//                                                    .placeholder(R.drawable.orderitem)
//                                                    .into(fragmentReturnOrderBinding.ivOrderdetailsimage)*/
//                                            }
//                                            2 -> {
//                                                btnpicked.visibility = android.view.View.VISIBLE
//                                                btnpicked.text = "Delivered"
//                                                btnpicked.background =
//                                                    ContextCompat.getDrawable(
//                                                        mainActivity,
//                                                        R.drawable.rectengel_green
//                                                    );
//
//                                                //  btnOrderprocess.visibility= android.view.View.GONE
//                                                //  btnNext.visibility= android.view.View.GONE
//                                                // llimageupload.visibility = android.view.View.VISIBLE
//
//                                                /*Picasso.get()
//                                                    .load(itProfileInfo.orderImageUrl + "/" + itProfileInfo.data.order.pickupImage)
//                                                    .error(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
//                                                    .placeholder(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
//                                                    .into(fragmentReturnOrderBinding.ivOrderdetailsimage)
//                                                Picasso.get()
//                                                    .load(itProfileInfo.orderImageUrl + "/" + itProfileInfo.data.order.deliveryImage)
//                                                    .error(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
//                                                    .placeholder(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
//                                                    .into(fragmentReturnOrderBinding.ivDeliverydetailsimage)*/
//                                            }
//                                            3 -> {
//                                                btnpicked.visibility = android.view.View.VISIBLE
//                                                btnpicked.text = "Pending"
//                                                btnpicked.background =
//                                                    ContextCompat.getDrawable(
//                                                        mainActivity,
//                                                        R.drawable.rectengel_red
//                                                    );
//
//                                                //  btnOrderprocess.visibility= android.view.View.GONE
//                                                // btnNext.visibility= android.view.View.VISIBLE
//                                                /* llimageupload.visibility = android.view.View.VISIBLE
//
//                                                 Picasso.get()
//                                                     .load(itProfileInfo.orderImageUrl + "/" + itProfileInfo.data.order.pickupImage)
//                                                     .error(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
//                                                     .placeholder(com.grocery.sainikgrocerydelivery.R.drawable.orderitem)
//                                                     .into(fragmentReturnOrderBinding.ivOrderdetailsimage)*/
//                                            }
//                                            else -> {
//                                                btnpicked.visibility = android.view.View.GONE
//
//                                                // btnOrderprocess.visibility= android.view.View.VISIBLE
//                                                // btnNext.visibility= android.view.View.GONE
//                                                //  llimageupload.visibility = android.view.View.GONE
//                                            }
//                                        }
//                                    }
//                                    fragmentReturnOrderBinding.tvorderDate.text =
//                                        "Order date : $formatted"
//                                    fragmentReturnOrderBinding.tvordId.text =
//                                        "Order ID : " + itProfileInfo.data.order.orderReferenceId
//                                    fragmentReturnOrderBinding.topnav.tvNavtitle.text =
//                                        itProfileInfo.data.order.orderReferenceId
//                                    fragmentReturnOrderBinding.tvAmount.text =
//                                        "Order Amount : ₹ " + itProfileInfo.data.order.amount
//                                    if (itProfileInfo.data.order.address != null) {
//                                        fragmentReturnOrderBinding.tvAddress.text =
//                                            itProfileInfo.data.order.address.houseNo + "," +
//                                                    itProfileInfo.data.order.address.apartmentName + "," +
//                                                    itProfileInfo.data.order.address.streetDetails + "," +
//                                                    itProfileInfo.data.order.address.city + "," +
//                                                    itProfileInfo.data.order.address.type
//                                    }
//                                    itemAdapter?.updateData(
//                                        itProfileInfo.data.order.orderHistory,
//                                        itProfileInfo.productImageUrl
//                                    )
//
//                                    if (itProfileInfo.data.order == null || itProfileInfo.data.order.orderHistory.isEmpty()) {
//
//                                        val navController =
//                                            Navigation.findNavController(fragmentReturnOrderBinding.root)
//                                        navController.navigate(R.id.nav_myorder)
//                                    }
//
//                                } else {
//
//                                    Toast.makeText(
//                                        mainActivity,
//                                        resource.data.message,
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//
//                                }
//                            }
//
//                        }
//                        Status.ERROR -> {
//                            mainActivity.hideProgressDialog()
//                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//
////                            if (it.message!!.contains("401", true)) {
////                                val builder = AlertDialog.Builder(this@LoginemailActivity)
////                                builder.setMessage("Invalid Employee Id / Password")
////                                builder.setPositiveButton(
////                                    "Ok"
////                                ) { dialog, which ->
////
////                                    dialog.cancel()
////
////                                }
////                                val alert = builder.create()
////                                alert.show()
////                            } else
////                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//
//                        }
//
//                        Status.LOADING -> {
//                            mainActivity.showProgressDialog()
//                        }
//
//                    }
//
//                }
//            }
//
//        } else {
//
//            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
//                .show()
//
//        }
//
//    }

    override fun onClick(position: Int, view: View, id: String, note: String) {


//        postReturn(id, note)
    }


//    private fun postReturn(id: Int, note: String) {
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            val productId = listOf(id)
//            viewModel.returnDelivery(
//                ReturnDeliveryRequestModel(productId, consignmentid.trim().toInt(), note)
//            ).observe(mainActivity) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            //mainActivity.hideLoading()
//                            resource.data?.let { itProfileInfo ->
//                                if (itProfileInfo.status) {
//                                    Toast.makeText(
//                                        mainActivity,
//                                        itProfileInfo.message,
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    consignmentlist(consignmentid.toString())
//                                } else {
//                                    Toast.makeText(
//                                        mainActivity,
//                                        "Review submition failed: ${itProfileInfo.message}",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//
//                                }
//                            }
//
//                        }
//                        Status.ERROR -> {
//                            mainActivity.hideProgressDialog()
//                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//
//
//                        }
//
//                        Status.LOADING -> {
//                            mainActivity.showProgressDialog()
//                        }
//
//                    }
//
//                }
//            }
//
//        } else {
//
//            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
//                .show()
//
//        }
//
//    }
}