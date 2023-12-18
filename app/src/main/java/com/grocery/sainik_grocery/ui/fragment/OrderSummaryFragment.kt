package com.grocery.sainik_grocery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartData
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartListRequest
import com.grocery.sainik_grocery.data.model.ordersummerymodel.OrdersummeryRequest
import com.grocery.sainik_grocery.data.model.postordermodel.PostOrderRequest
import com.grocery.sainik_grocery.data.model.postordermodel.SalesOrderItem
import com.grocery.sainik_grocery.data.model.postordermodel.UnitConversation
import com.grocery.sainik_grocery.data.model.salesordermodel.SalesOrderPaymentRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentOrderSummaryBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.MainActivity.Companion.payment
import com.grocery.sainik_grocery.ui.adapter.CartCountAdapter
import com.grocery.sainik_grocery.ui.adapter.OrderDetailsListAdapter
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random


class OrderSummaryFragment : Fragment(), OrderDetailsListAdapter.OnItemClickListener,
    CartCountAdapter.OnItemClickListener{

    lateinit var mainActivity: MainActivity
    lateinit var fragmentOrderSummaryBinding: FragmentOrderSummaryBinding
    private lateinit var viewModel: CommonViewModel
    var salesOrderItem = ArrayList<SalesOrderItem>()
    var addressId = ""
    var deliverycharges = ""
    var ordernumber = ""
    var total = ""
    var totalAmount = ""
    var discount = ""
    var cartCountAdapter:CartCountAdapter?=null
    var dialog:BottomSheetDialog?=null
    var advanceorder = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderSummaryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_summary, container, false)
        val root = fragmentOrderSummaryBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm
        return root
    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(false)

        if (payment.equals("success")){

            ordernumbergeneration("Online")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentOrderSummaryBinding.topnav.tvNavtitle.text = "Order summary"

        with(fragmentOrderSummaryBinding) {

            cartCountAdapter = CartCountAdapter(mainActivity, this@OrderSummaryFragment)
            rvOrderdetails.adapter = cartCountAdapter
            rvOrderdetails.layoutManager = GridLayoutManager(mainActivity, 1)

            topnav.btnBack.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }
                mainActivity.onBackPressedDispatcher.onBackPressed()
            }

            topnav.clCart.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_cart)


            }

            topnav.btnWishlist.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_wishlist)

            }
            btnAddressChange.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_address)

            }

            if (HomeFragment.cartCount > 0) {
                topnav.tvCartCount.text = HomeFragment.cartCount.toString()
                topnav.cvCartCount.visibility = View.VISIBLE
            } else {
                topnav.cvCartCount.visibility = View.GONE
            }

            btnAddressChange.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_address)

            }
            topnav.ivNotification.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_notification)

            }
            btnCheckout.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }

                if (total.isNotEmpty()){
                    if (fragmentOrderSummaryBinding.tvAddress.text.toString().isNotEmpty()){

                        showBottomDialog(total)

//                        mainActivity.startPayment(total)

                    }else{

                        Toast.makeText(
                            mainActivity,
                            "Select Address For Payment",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }else{

                    Toast.makeText(mainActivity, "No Item Found", Toast.LENGTH_SHORT).show()
                }


            }
        }
        getAddressList()
        getOrderSummery()


    }




    private fun showBottomDialog(total:String) {
        dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
        dialog!!.setContentView(R.layout.bottom_sheet_payment)
        val btnCOD = dialog!!.findViewById<AppCompatButton>(R.id.btnCOD)
        val btnOnline = dialog!!.findViewById<AppCompatButton>(R.id.btnOnline)
        dialog!!.show()
        btnCOD!!.setOnClickListener {
//            val navController = Navigation.findNavController(fragmentOrderSummaryBinding.root)
//            navController.navigate(R.id.nav_addaddress)
            dialog!!.dismiss()
            ordernumbergeneration("Offline")
//            openSearchBar()
//            dialog.dismiss()
        }


        btnOnline!!.setOnClickListener {
//            val navController = Navigation.findNavController(fragmentOrderSummaryBinding.root)
//            navController.navigate(R.id.nav_addaddress)
            dialog!!.dismiss()
            mainActivity.startPayment(total)

//            openSearchBar()
//            dialog.dismiss()
        }




    }




    private fun getAddressList() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.addresslist(customerid = Shared_Preferences.getUserId())
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                // mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {
                                        if (itResponse.data.isNullOrEmpty()) {

                                            Toast.makeText(
                                                mainActivity,
                                                "Add address first to continue.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val navController = Navigation.findNavController(
                                                fragmentOrderSummaryBinding.root
                                            )
                                            navController.navigate(R.id.nav_addaddress)
                                            return@let
                                        }
                                        itResponse.data.forEach { item ->
                                            if (item.isPrimary == true) {
                                                fragmentOrderSummaryBinding.tvAddress.text =
                                                    item.landMark + " " + item.houseNo + " " + item.streetDetails
                                                fragmentOrderSummaryBinding.tvAddress.tag = item.id
                                                addressId = item.id
                                                return@forEach
                                            }
                                        }

                                        // addressAdapter?.updateData(itResponse.data.address)

                                    } else {

//                                        Toast.makeText(
//                                            mainActivity,
//                                            resource.data?.message,
//                                            Toast.LENGTH_SHORT
//                                        ).show()

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

    private fun productCartList() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.CartList(
                CartListRequest(
                    customerId = Shared_Preferences.getUserId(),
                    productMainCategoryId = Shared_Preferences.getMaincatid().toString(),
                    pageSize = 100,
                    skip = 0
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data.let { itResponse ->

                                    if (itResponse?.status == true) {

                                        cartCountAdapter?.updateData(itResponse.data)
                                        fragmentOrderSummaryBinding.tvItemQtyTxt.text = (if (itResponse.data.size.toFloat() > 1) "Basket Value(${itResponse.data.size} Items)" else "Basket Value(${itResponse.data.size} Items)").toString()


                                        if (itResponse.data[0].isAdvanceOrderRequest == false){
                                            advanceorder = false
                                        }else{
                                            advanceorder = true
                                        }

                                        for (i in 0 until itResponse.data.size) {
                                            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                            val orderDate = sdf.format(Date())

                                            salesOrderItem.add(
                                                SalesOrderItem(
                                                    "",
                                                    "",
                                                    itResponse.data[i].discount,
                                                    itResponse.data[i].discount,
                                                    itResponse.data[i].productId,
                                                    itResponse.data[i].productName,
                                                    itResponse.data[i].quantity,
                                                    arrayListOf(),
                                                    "",
                                                    orderDate,
                                                    "0",
                                                    "0",
                                                    itResponse.data[i].unitPrice.toString(),
                                                    UnitConversation(
                                                        "Kilo Gram",
                                                        "KG",
                                                        "",
                                                        "",
                                                        "",
                                                        ""
                                                    ),
                                                    itResponse.data[i].unitId,
                                                    itResponse.data[i].unitName,
                                                    itResponse.data[i].unitPrice,
                                                    "",
                                                    ""
                                                )
                                            )
                                        }


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

    //
    private fun getOrderSummery() {


        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.ordersummery(OrdersummeryRequest(customerId = Shared_Preferences.getUserId(), productMainCategoryId=Shared_Preferences.getMaincatid().toString()))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {


                                        with(fragmentOrderSummaryBinding) {
//                                            tvItemQtyTxt.text = if (itResponse.data.items > 1) "Price (${itResponse.data.items} Items)" else "Price (${itResponse.data.items} Item)"
                                            tvPrice.text = "₹ ${itResponse.data.price}"
                                            totalAmount = itResponse.data.price.toString()
                                            tvDeliveryCharge.text =
                                                "₹ ${itResponse.data.deliveryCharges}"
                                            deliverycharges =
                                                itResponse.data.deliveryCharges.toString()
                                            tvDiscount.text = "₹ ${itResponse.data.discount}"
                                            discount = itResponse.data.discount.toString()
//                                            total = ((itResponse.data.price.toDouble() - itResponse.data.discount.toDouble()) + itResponse.data.deliveryCharges.toDouble()).toString()
                                            total = String.format("%.2f", ((itResponse.data.price.toDouble() - itResponse.data.discount.toDouble()) + itResponse.data.deliveryCharges.toDouble()))
                                            tvTotalPrice.text = "₹ $total"
                                        }

                                        productCartList()

//                                        println("PRICE SUMMERY $total")
                                        // addressAdapter?.updateData(itResponse.data.address)

                                    } else {

                                        Toast.makeText(
                                            mainActivity,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

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




    private fun ordernumbergeneration(payment:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.GetNewOrderNumber()
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {


                                        postOrder(itResponse.orderNumber, payment)




//                                        println("PRICE SUMMERY $total")
                                        // addressAdapter?.updateData(itResponse.data.address)

                                    } else {

                                        Toast.makeText(
                                            mainActivity,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

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




    private fun postOrder(number:String, paymentType:String) {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val orderDate = sdf.format(Date())
            viewModel.postorder(
                PostOrderRequest(
                    customerId = Shared_Preferences.getUserId(),
                    PaymentType = paymentType,
                    productMainCategoryId = Shared_Preferences.getMaincatid().toString(),
                    isAppOrderRequest = true,
                    IsAdvanceOrderRequest = advanceorder,
                    customerName = Shared_Preferences.getName(),
                    deliveryAddress = fragmentOrderSummaryBinding.tvAddress.text.toString(),
                    deliveryAddressId = addressId,
                    deliveryCharges = deliverycharges,
                    deliveryDate = "2023-11-20T09:50:00.279Z",
                    deliveryStatus = "0",
                    isSalesOrderRequest = false,
                    note = "",
                    orderNumber = number,
                    paymentStatus = "1",
                    salesOrderItem,
                    soCreatedDate = orderDate,
                    status = "0",
                    termAndCondition = "Test Term & Condition - Phone",
                    totalAmount = totalAmount,
                    totalDiscount = discount,
                    totalPaidAmount = total,
                    totalTax = "0"
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            resource.data?.let { itResponse ->

                                if (itResponse.status) {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
//                                    val intent = Intent(mainActivity, MainActivity::class.java)
//                                    startActivity(intent)
//                                    mainActivity.finish()

                                    salesorderpayment(resource.data.salesOrderId, orderDate, total, paymentType)
                                    payment = ""
                                    val bundle = Bundle()
                                    bundle.putString("address", fragmentOrderSummaryBinding.tvAddress.text.toString())
                                    bundle.putString("amount", total)
                                    val navController = Navigation.findNavController(fragmentOrderSummaryBinding.root)
                                    navController.navigate(R.id.nav_succeess, bundle)

                                } else {

                                    Toast.makeText(
                                        mainActivity,
                                        resource.data?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }

                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
//                            val builder = AlertDialog.Builder(mainActivity)
//                            builder.setMessage(it.message)
//                            builder.setPositiveButton(
//                                "Ok"
//                            ) { dialog, which ->
//
//                                dialog.cancel()
//
//                            }
//                            val alert = builder.create()
//                            alert.show()
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


    override fun onDestroy() {
        super.onDestroy()
        payment = ""

    }


    private fun salesorderpayment(salesorderid:String, orderDate:String, total:String, paymentType:String){


        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.SalesOrderPayment(SalesOrderPaymentRequest(amount=total, attachmentData="",
                attachmentUrl="", note=paymentType, paymentDate = orderDate, paymentMethod = 0, referenceNumber = "RFN-COD",
                salesOrderId = salesorderid))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

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


    fun generateUniqueNumber(): Int {
        val currentTimeMillis = System.currentTimeMillis()
        val random = Random(currentTimeMillis)
        return random.nextInt()
    }



//
//    private fun postOrderDetails(view: View) {
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            viewModel.addOrderDetails(
//                AddOrderRequestModel(
//                    fragmentOrderSummaryBinding.tvAddress.tag.toString().toInt()
//                )
//            )
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                resource.data?.let { itResponse ->
//
//                                    if (itResponse.status) {
//
//                                        with(fragmentOrderSummaryBinding) {
//                                            tvItemQtyTxt.text =
//                                                if (itResponse.data.items > 1) "Price (${itResponse.data.items} Items)" else "Price (${itResponse.data.items} Item)"
//                                            tvPrice.text = "₹ ${itResponse.data.price}"
//                                            tvDeliveryCharge.text =
//                                                "₹ ${itResponse.data.deliveryCharges}"
//                                            tvDiscount.text = "₹ ${itResponse.data.discount}"
//                                            val total =
//                                                (itResponse.data.price - itResponse.data.discount) + itResponse.data.deliveryCharges
//                                            tvTotalPrice.text = "₹ $total"
//
//                                            val bundle = Bundle()
//                                            bundle.putString("orderId", itResponse.data.order.id.toString())
//                                            bundle.putString("refId", itResponse.data.order.orderReferenceId)
//                                            bundle.putString("price", total.toString())
//                                            /* val navController = Navigation.findNavController(view)
//                                            navController.navigate(R.id.nav_save_card,bundle)*/
//
//
//                                            println("PRICE SUMMERY $total")
//                                            mainActivity.startPayment(itResponse.data.order.id.toString(), total.toString(), itResponse.data.order.orderReferenceId)
//
//                                        }
//                                        // addressAdapter?.updateData(itResponse.data.address)
//
//                                    } else {
//
//                                        Toast.makeText(
//                                            mainActivity,
//                                            resource.data?.message,
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//
//                                    }
//                                }
//
//                            }
//                            Status.ERROR -> {
//                                mainActivity.hideProgressDialog()
//                                val builder = AlertDialog.Builder(mainActivity)
//                                builder.setMessage(it.message)
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.show()
//                            }
//
//                            Status.LOADING -> {
//                                mainActivity.showProgressDialog()
//                            }
//
//                        }
//
//                    }
//                }
//
//        } else {
//            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
//                .show()
//        }
//
//    }



    override fun onClick(
        position: Int,
        view: View,
        clickType: Int,
        cartData: CartData,
        count: Int,
        textView: TextView
    ) {



    }

    override fun onClick(
        position: Int,
        salesOrderItem: com.grocery.sainik_grocery.data.model.orderdetailsmodel.SalesOrderItem,
        view: View
    ) {


    }


}