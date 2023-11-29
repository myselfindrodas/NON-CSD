package com.grocery.sainik_grocery.ui.fragment

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sainikgrocerycustomer.data.model.OrderHistory
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.orderdetailsmodel.OrderdetailsRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentOrderDetailsBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.OrderDetailsListAdapter
import com.grocery.sainik_grocery.ui.adapter.TimeLineBaseAdapter
import com.grocery.sainik_grocery.utils.ItemOffsetDecoration
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import com.lriccardo.timelineview.TimelineDecorator
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class OrderDetailsFragment : Fragment(), OrderDetailsListAdapter.OnItemClickListener {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentOrderDetailsBinding: FragmentOrderDetailsBinding
    private var productAdapter: OrderDetailsListAdapter? = null
    private lateinit var viewModel: CommonViewModel
    var orderId=""
    var urcId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false)
        val root = fragmentOrderDetailsBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }
        viewModel = vm
        val intent = arguments
        if (intent != null && intent.containsKey("orderId")) {
            orderId = intent.getString("orderId","")
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentOrderDetailsBinding.topnav.tvNavtitle.text = "Order details"

        with(fragmentOrderDetailsBinding) {
            productAdapter =
                OrderDetailsListAdapter(
                    mainActivity,
                    this@OrderDetailsFragment
                )
            rvProductList.adapter = productAdapter
            rvProductList.layoutManager = GridLayoutManager(mainActivity, 1)
            val itemDecoration = ItemOffsetDecoration(mainActivity, R.dimen.photos_list_spacing)
            // rvProductList.addItemDecoration(itemDecoration)
//            val orderDetailsList = ArrayList<OrderHistory>()
//            for (i in 1..5){
//                orderDetailsList.add(OrderHistory("2023-04-11T06:02:53.000000Z","", 1, 4, 132.3, "shop", "", 2, "100gm", 2, 3, 1, 1, 44.2, "023-04-11T06:02:53.000000Z", 2))
//            }
//            productAdapter!!.updateData(orderDetailsList, "R.drawable.item")


            topnav.clCart.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_cart)

            }

            if (HomeFragment.cartCount > 0) {
                topnav.tvCartCount.text = HomeFragment.cartCount.toString()
                topnav.cvCartCount.visibility = View.VISIBLE
            } else {
                topnav.cvCartCount.visibility = View.GONE
            }

            topnav.ivNotification.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_notification)

            }


            topnav.btnBack.setOnClickListener {

                mainActivity.onBackPressedDispatcher.onBackPressed()
            }

            topnav.btnWishlist.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_wishlist)

            }


            llReturn.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("orderId", orderId)
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_returnorder,bundle)

            }

        }
        getMyOrderDetails(orderId)
    }

//    private fun getTimelineView(
//        position: Int,
//        date: List<String>,
//        expectedDate: String,
//        expectedTime: String
//    ) {
//
//        with(fragmentOrderDetailsBinding) {
//
//            timelineRv.let {
//
//                val timelineModelArrayList: ArrayList<TimeLineModel> = ArrayList<TimeLineModel>()
//                timelineModelArrayList.add(
//                    TimeLineModel(
//                        "Order Placed",
//                        date[0],
//                        "Your Order has been placed. Delivery on $expectedDate"
//                    )
//                )
//                timelineModelArrayList.add(
//                    TimeLineModel(
//                        "Items Processed",
//                        date[1],
//                        "Your items has been processed"
//                    )
//                )
//                timelineModelArrayList.add(
//                    TimeLineModel(
//                        "Delivery",
//                        date[2],
//                        "Our delivery person are on their way to bring your Order."
//                    )
//                )
//                timelineModelArrayList.add(
//                    TimeLineModel(
//                        "Shipping Delivered.",
//                        date[3],
//                        "Expected time $expectedTime"
//                    )
//                )
//
//                it.layoutManager =
//                    LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
//                it.adapter = TimeLineBaseAdapter(mainActivity, timelineModelArrayList, position)
//
//                val colorPrimary = TypedValue()
//                val theme: Resources.Theme = mainActivity.theme
//                theme.resolveAttribute(R.color.orange, colorPrimary, true)
//
//                it.addItemDecoration(
//                    TimelineDecorator(
//                        indicatorSize = 28f,
//                        lineWidth = 10f,
//                        padding = 30f,
//                        position = TimelineDecorator.Position.Left,
//                        indicatorColor = Color.GREEN,
//                        lineColor = Color.GREEN
//                    )
//                )
//
//                /*it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                        super.onScrolled(recyclerView, dx, dy)
//                        (it.layoutManager as? LinearLayoutManager)?.let {
//                            if (it.findFirstCompletelyVisibleItemPosition() == 0)
//                                binding.fab.extend()
//                            else
//                                binding.fab.shrink()
//                        }
//                    }
//                })*/
//            }
//        }
//    }

    @SuppressLint("SetTextI18n")
    private fun getMyOrderDetails(orderId:String) {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.orderdetails(OrderdetailsRequest(id= orderId, pageSize = 10, skip = 0))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {
                                        with(fragmentOrderDetailsBinding) {
                                            tvItemQtyTxt.text =
                                                if (itResponse.data.salesOrderItems.size > 1) "Price (${itResponse.data.salesOrderItems.size} Items)" else "Price (${itResponse.data.salesOrderItems.size} Item)"
                                            tvPrice.text = "₹ ${itResponse.data.totalAmount}"
                                            tvDeliveryCharge.text =
                                                "₹ ${itResponse.data.deliveryCharges}"
                                            tvDiscount.text = "₹ ${itResponse.data.totalDiscount}"
//                                            val total =
//                                                (itResponse.data.order.amount - itResponse.data.order.discount) + itResponse.data.order.deliveryCharges
                                            val total = itResponse.data.totalAmount + itResponse.data.deliveryCharges
                                            tvTotalPrice.text = "₹ $total"

//                                            tvAddress.text =
//                                                itResponse.data.order.address.apartmentName + " " + itResponse.data.order.address.houseNo +
//                                                        " " + itResponse.data.order.address.streetDetails + "\n" +
//                                                        itResponse.data.order.address.city + ", " + itResponse.data.order.address.pincode
                                            tvAddress.text = itResponse.data.deliveryAddress
                                            tvAddress.tag = itResponse.data.deliveryAddressId
                                        }
                                        productAdapter?.updateData(itResponse.data.salesOrderItems)

//                                        val date: ArrayList<String> = arrayListOf()
//
//                                        val expectedDate =
//                                            getFormatedDateTime(itResponse.data.order.expt_delivery)
//                                        println("LIST SIZE==${itResponse.data.order.status_list.size}")
//                                        if (itResponse.data.order.status_list.size == 4) {
//
//                                            itResponse.data.order.status_list.forEach { item ->
//                                                date.add(getFormatedDate(item.createdAt))
//                                            }
//
//                                            fragmentOrderDetailsBinding.llReturn.visibility=View.VISIBLE
//                                        } else if (itResponse.data.order.status_list.size == 3) {
//                                            itResponse.data.order.status_list.forEach { item ->
//                                                date.add(getFormatedDate(item.createdAt))
//                                            }
//                                            date.add("")
//                                            fragmentOrderDetailsBinding.llReturn.visibility=View.GONE
//                                        } else if (itResponse.data.order.status_list.size == 2) {
//                                            itResponse.data.order.status_list.forEach { item ->
//                                                date.add(getFormatedDate(item.createdAt))
//                                            }
//                                            date.add("")
//                                            date.add("")
//                                            fragmentOrderDetailsBinding.llReturn.visibility=View.GONE
//                                        } else if (itResponse.data.order.status_list.size == 1) {
//                                            itResponse.data.order.status_list.forEach { item ->
//                                                date.add(getFormatedDate(item.createdAt))
//                                            }
//                                            date.add("")
//                                            date.add("")
//                                            date.add("")
//                                            fragmentOrderDetailsBinding.llReturn.visibility=View.GONE
//                                        } else {
//                                            date.add("")
//                                            date.add("")
//                                            date.add("")
//                                            date.add("")
//                                            fragmentOrderDetailsBinding.llReturn.visibility=View.GONE
//                                        }
//                                        getTimelineView(
//                                            itResponse.data.order.status_list.size,
//                                            date,
//                                            expectedDate[0],
//                                            expectedDate[1]
//                                        )
//                                        fragmentOrderDetailsBinding.llDeliveryAddress.visibility=View.VISIBLE
//                                        fragmentOrderDetailsBinding.clPriceDetails.visibility=View.VISIBLE
//                                    } else {
//
//                                        fragmentOrderDetailsBinding.llDeliveryAddress.visibility=View.GONE
//                                        fragmentOrderDetailsBinding.clPriceDetails.visibility=View.GONE
//                                        Toast.makeText(
//                                            mainActivity,
//                                            resource.data?.message,
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//
//                                    }
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
                                fragmentOrderDetailsBinding.llDeliveryAddress.visibility=View.VISIBLE
                                fragmentOrderDetailsBinding.clPriceDetails.visibility=View.VISIBLE
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

    private fun getFormatedDate(inputDate: String): String {

        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat("dd-MM-yyyy")

        var d: Date? = null
        try {
            d = input.parse(inputDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        return output.format(d!!)
    }

    private fun getFormatedDateTime(inputTime: String): List<String> {
        // 2023-04-13 15:53:42
        val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputDate = SimpleDateFormat("dd MMM yyyy")

        val outputTime = SimpleDateFormat("hh:mm a")
        var d: Date? = null
        try {
            d = input.parse(inputTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        return listOf(outputDate.format(d), outputTime.format(d))
    }

    override fun onClick(position: Int, view: View) {

    }

}