package com.grocery.sainik_grocery.ui.fragment

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.addtocartmodel.AddtocartRequest
import com.grocery.sainik_grocery.data.model.deletecartmodel.DeleteCartRequest
import com.grocery.sainik_grocery.data.model.deletefullcartmodel.DeleteCustomerCartRequest
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartListRequest
import com.grocery.sainik_grocery.data.model.productdetailsmodel.ProductDetailsRequest
import com.grocery.sainik_grocery.data.model.productlistmodel.ProductListRequest
import com.grocery.sainik_grocery.data.model.updatecartmodel.CartUpdateRequest
import com.grocery.sainik_grocery.data.model.wishlistaddmodel.WishListAddRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentProductDetailsBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.PackSizeAdapter
import com.grocery.sainik_grocery.ui.adapter.RelatedProductAdapter
import com.grocery.sainik_grocery.ui.fragment.ProductListFragment.Companion.categoryId
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import com.squareup.picasso.Picasso

class ProductDetailsFragment : Fragment(), RelatedProductAdapter.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    lateinit var fragmentProductDetailsBinding: FragmentProductDetailsBinding
    private var productAdapter: RelatedProductAdapter? = null

    //    private var productItemAdapter: ProductItemAdapter? = null
    private var packSizeAdapter: PackSizeAdapter? = null
    private var imageURL: String="https://maitricomplex.in/"

    private lateinit var viewModel: CommonViewModel
    private var productId = ""
    private var urc_productId = ""
    private var wishlistId = ""
    private var discount = ""
    private var price = ""
    private var unitid = ""
    private var unitname = ""
    private var unitprice = ""
    private var partyadd = ""
    var advanceisadded=false
    var count=0
    var cartid = ""
    var productdetails = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        val root = fragmentProductDetailsBinding.root
        mainActivity = activity as MainActivity
        fragmentProductDetailsBinding.topnav.tvNavtitle.text = ""

        val intent = arguments
        if (intent != null && intent.containsKey("productid")) {
            productId = intent.getString("productid", "")
        }

        if (intent != null && intent.containsKey("type")) {
            partyadd = intent.getString("type", "")
        }

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm


        return root
    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = RelatedProductAdapter(mainActivity, this@ProductDetailsFragment)
        fragmentProductDetailsBinding.rvSimilarproducts.adapter = productAdapter
        fragmentProductDetailsBinding.rvSimilarproducts.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)


        fragmentProductDetailsBinding.topnav.tvNavtitle.text = "About Item"

        fragmentProductDetailsBinding.topnav.btnBack.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        fragmentProductDetailsBinding.topnav.clCart.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)

        }

        fragmentProductDetailsBinding.btnAddressChange.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_address)

        }

        fragmentProductDetailsBinding.topnav.ivNotification.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)

        }

        fragmentProductDetailsBinding.topnav.btnWishlist.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wishlist)

        }

        fragmentProductDetailsBinding.llPackTxt.setOnClickListener {

            if (fragmentProductDetailsBinding.rvPackSize.isVisible) {
                fragmentProductDetailsBinding.rvPackSize.visibility = View.GONE
                fragmentProductDetailsBinding.ivPackArrow.rotation = 90f

            } else {
                fragmentProductDetailsBinding.rvPackSize.visibility = View.VISIBLE
                fragmentProductDetailsBinding.ivPackArrow.rotation = 270f
            }
        }


        fragmentProductDetailsBinding.btnAddtoproduct.setOnClickListener {

//            CartList(true)

            Log.d(TAG, "party-->"+partyadd)
            Log.d(TAG, "advanceisadded-->"+advanceisadded)

            if (partyadd.equals("partyadd")){

                if (advanceisadded==true){

                    if (price.isNullOrEmpty()){
                        Toast.makeText(
                            mainActivity,
                            "Price Not Valid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        productaddtoCart()
                    }

                }else{

                    val builder = AlertDialog.Builder(mainActivity)
                    builder.setMessage(
                        "You Have Some of regular order already added inside cart!" +
                                " Please Delete previous cart items for regular product add"
                    )
                    builder.setPositiveButton(
                        "Ok"
                    ) { dialog, which ->

                        DeleteCustomerCart()
                        dialog.cancel()
                    }

                    builder.setNegativeButton("Cancel") { dialog, which ->
                        val intent = Intent(mainActivity, MainActivity::class.java)
                        startActivity(intent)
                        mainActivity.finish()
                        dialog.cancel()
                    }
                    val alert = builder.create()
                    alert.setOnShowListener { arg0: DialogInterface? ->
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(
                                resources.getColor(
                                    R.color.blue,
                                    resources.newTheme()
                                )
                            )
                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(
                                resources.getColor(
                                    R.color.blue,
                                    resources.newTheme()
                                )
                            )
                    }
                    alert.show()
                }
            }else{

                if (advanceisadded==false){
                    if (price.isNullOrEmpty()){
                        Toast.makeText(
                            mainActivity,
                            "Price Not Valid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        productaddtoCart()
                    }

                }else{

                    val builder = AlertDialog.Builder(mainActivity)
                    builder.setMessage(
                        "You Have Some of party order already added inside cart!" +
                                " Please Delete previous cart items for regular product add"
                    )
                    builder.setPositiveButton(
                        "Ok"
                    ) { dialog, which ->

                        DeleteCustomerCart()
                        dialog.cancel()
                    }

                    builder.setNegativeButton("Cancel") { dialog, which ->
                        val intent = Intent(mainActivity, MainActivity::class.java)
                        startActivity(intent)
                        mainActivity.finish()
                        dialog.cancel()
                    }
                    val alert = builder.create()
                    alert.setOnShowListener { arg0: DialogInterface? ->
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(
                                resources.getColor(
                                    R.color.blue,
                                    resources.newTheme()
                                )
                            )
                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(
                                resources.getColor(
                                    R.color.blue,
                                    resources.newTheme()
                                )
                            )
                    }
                    alert.show()
                }


            }


        }






        fragmentProductDetailsBinding.btnAddWishlist.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            addtoWishlist(Shared_Preferences.getUserId(), productId, it)

        }

        fragmentProductDetailsBinding.tvCounter.text = count.toString()


        fragmentProductDetailsBinding.btnSub.setOnClickListener {

            if (count >= 1) {
                count--
                fragmentProductDetailsBinding.tvCounter.text = count.toString()
                onUpdate(count, "update", 0)

            }else{

                count = 0
                fragmentProductDetailsBinding.tvCounter.text = count.toString()
                fragmentProductDetailsBinding.btnAddtoproduct.visibility = View.VISIBLE
                fragmentProductDetailsBinding.llcounter.visibility = View.GONE

            }

            if (fragmentProductDetailsBinding.tvCounter.text.toString().equals("0")){
                fragmentProductDetailsBinding.btnAddtoproduct.visibility = View.VISIBLE
                fragmentProductDetailsBinding.llcounter.visibility = View.GONE
            }
            /*tvCounter.text = count.toString()*/
        }

        fragmentProductDetailsBinding.btnAdd.setOnClickListener {
            count++
            fragmentProductDetailsBinding.tvCounter.text = count.toString()
            onUpdate(count, "update", 1)

            /*tvCounter.text = count.toString()*/
        }


        fragmentProductDetailsBinding.btnaboutproductExpand.setOnClickListener {

            fragmentProductDetailsBinding.btnaboutproductExpand.visibility = View.GONE
            fragmentProductDetailsBinding.btnaboutproductClose.visibility = View.VISIBLE
            fragmentProductDetailsBinding.llaboutproduct.visibility = View.VISIBLE

        }


        fragmentProductDetailsBinding.btnaboutproductClose.setOnClickListener {


            fragmentProductDetailsBinding.btnaboutproductExpand.visibility = View.VISIBLE
            fragmentProductDetailsBinding.btnaboutproductClose.visibility = View.GONE
            fragmentProductDetailsBinding.llaboutproduct.visibility = View.GONE
        }


        fragmentProductDetailsBinding.btnbenifitproductExpand.setOnClickListener {

            fragmentProductDetailsBinding.btnbenifitproductExpand.visibility = View.GONE
            fragmentProductDetailsBinding.btnbenifitproductClose.visibility = View.VISIBLE
            fragmentProductDetailsBinding.llbenifits.visibility = View.VISIBLE
        }



        fragmentProductDetailsBinding.btnbenifitproductClose.setOnClickListener {

            fragmentProductDetailsBinding.btnbenifitproductExpand.visibility = View.VISIBLE
            fragmentProductDetailsBinding.btnbenifitproductClose.visibility = View.GONE
            fragmentProductDetailsBinding.llbenifits.visibility = View.GONE
        }


        getProductDetails()
        getAddressList()
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
                                                fragmentProductDetailsBinding.root
                                            )
                                            navController.navigate(R.id.nav_addaddress)
                                            return@let
                                        }
                                        itResponse.data.forEach { item ->
                                            if (item.isPrimary == true) {
                                                fragmentProductDetailsBinding.tvAddress.text =
                                                    item.landMark + " " + item.houseNo + " " + item.streetDetails
                                                fragmentProductDetailsBinding.tvAddress.tag = item.id
                                                return@forEach
                                            }
                                        }

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



    private fun getProductDetails() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.productDetails(ProductDetailsRequest(productId.toString()))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itProfileInfo ->


                                    try {
                                        Picasso.get()
                                            .load(imageURL +itProfileInfo.data.productUrl)
                                            .error(R.drawable.noimagefound)
//                                        .placeholder(R.drawable.loader_gif)
                                            .into(fragmentProductDetailsBinding.ivProductmain)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
//                                productItemAdapter?.updateData(itProfileInfo.data.productDetails.product.productImages,itProfileInfo.productImageUrl)
                                    //packSizeAdapter?.updateData(itProfileInfo.data.productDetails.productPackSize)

//                                productAdapter?.updateData(itProfileInfo.data.similarProducts as ArrayList<DataProductList>,itProfileInfo.productImageUrl)
                                    with(fragmentProductDetailsBinding) {

                                        tvProductName.setText(convertToCamelCase(itProfileInfo.data.name))
                                        if (itProfileInfo.data.discount > 0) {
                                            tvDiscount.text = "${itProfileInfo.data.discount}% Off"
                                            discount = itProfileInfo.data.discount.toString()
                                            llDiscount.visibility = View.VISIBLE
//                                            tvPriceOld.visibility = View.VISIBLE
                                        } else {
                                            llDiscount.visibility = View.GONE
//                                            tvPriceOld.visibility = View.GONE
                                            discount = "0"
                                        }



                                        tvPrice.text = " ₹ ${itProfileInfo.data.salesPrice.toString()}/${itProfileInfo.data.unitName ?: ""}"
                                        llaboutproduct.text = itProfileInfo.data.description?:""
                                        tvPriceOld.text = "₹ " +itProfileInfo.data.mrp
                                        tvSaveAmount.text = "Save ₹ "+ String.format("%.2f", itProfileInfo.data.mrp.toDouble().minus(itProfileInfo.data.salesPrice.toDouble()))
                                        llbenifits.text = "Save ₹ "+ String.format("%.2f", itProfileInfo.data.mrp.toDouble().minus(itProfileInfo.data.salesPrice.toDouble()))
                                        tvPriceOld.paintFlags = tvPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                                        urc_productId = itProfileInfo.data.id
                                        wishlistId = itProfileInfo.data.id
                                        if (itProfileInfo.data.salesPrice==null){
                                            price = ""
                                        }else{
                                            price = itProfileInfo.data.salesPrice

                                        }
                                        unitid = itProfileInfo.data.unitId.toString()
                                        unitname = itProfileInfo.data.unitName.toString()
//                                    rateProduct.rating = itProfileInfo.data.productDetails.avgRating.toFloat()
                                        rateProduct.rating = 5.0F
                                    }
                                    getSimilarproduct()
                                    CartList(true)

                                    // categoryListAdapter?.updateData(itProfileInfo.data.category as ArrayList<Category>)

                                }

                            }

                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                Log.d(ContentValues.TAG, "print-->" + resource.data?.status)

//                            if (it.message!!.contains("401", true)) {
//                                val builder = AlertDialog.Builder(this@LoginemailActivity)
//                                builder.setMessage("Invalid Employee Id / Password")
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.show()
//                            } else
//                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

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

    private fun getSimilarproduct() {

        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.productList(
                ProductListRequest("1", "0", categoryId)
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (resource.data?.status == true) {
                                resource.data?.let { itProfileInfo ->
                                    productAdapter?.updateData(itProfileInfo.data)
                                }
                            }
                            mainActivity.hideProgressDialog()


                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)

//                            if (it.message!!.contains("401", true)) {
//                                val builder = AlertDialog.Builder(this@LoginemailActivity)
//                                builder.setMessage("Invalid Employee Id / Password")
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.show()
//                            } else
//                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

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


    private fun productaddtoCart() {

        var isPartyOrder = false
        if (partyadd.equals("partyadd")){
            isPartyOrder = true
        }else{
            isPartyOrder = false
        }
        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.AddToCart(AddtocartRequest(
                    customerId = Shared_Preferences.getUserId(),
                    productMainCategoryId = Shared_Preferences.getMaincatid().toString(),
                    isAdvanceOrderRequest = isPartyOrder,
                    customerName = Shared_Preferences.getName().toString(),
                    discount = discount,
                    discountPercentage = discount,
                    productId = urc_productId,
                    productName = fragmentProductDetailsBinding.tvProductName.text.toString(),
                    quantity = "1",
                    taxValue = "0",
                    total = price,
                    unitId = unitid,
                    unitName = unitname,
                    unitPrice = price
                )).observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                if (resource.data?.status == true) {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
                                    fragmentProductDetailsBinding.btnAddtoproduct.visibility = View.GONE
                                    fragmentProductDetailsBinding.llcounter.visibility = View.VISIBLE
                                    count++
                                    fragmentProductDetailsBinding.tvCounter.text = count.toString()
                                    CartList(true)
//                                    productCartList()
//                                    val builder = android.app.AlertDialog.Builder(mainActivity)
//                                    builder.setMessage(resource.data.message)
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
//                                        val navController = Navigation.findNavController(fragmentProductDetailsBinding.root)
//                                        navController.navigate(R.id.nav_cart)
//                                        dialog.dismiss()
//                                    }
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0 ->
//                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.orange))
//                                    }
//                                    alert.show()

                                } else {

                                    Toast.makeText(
                                        mainActivity,
                                        resource.data?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

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



    private fun CartList(isFirstPage: Boolean) {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.CartList(
                CartListRequest(
                    customerId = Shared_Preferences.getUserId(),
                    productMainCategoryId = Shared_Preferences.getMaincatid().toString(),
                    pageSize = 10,
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

                                        advanceisadded = itResponse.data[0].isAdvanceOrderRequest

                                        if (itResponse.data.isNotEmpty()) {
                                            for (i in 0 until itResponse.data.size) {
                                                if (itResponse.data[i].productId == productId) {
                                                    count = itResponse.data[i].quantity
                                                    cartid = itResponse.data[i].id

                                                        fragmentProductDetailsBinding.tvCounter.text = count.toString()
                                                    if (count > 0) {
                                                        fragmentProductDetailsBinding.btnAddtoproduct.visibility = View.GONE
                                                        fragmentProductDetailsBinding.llcounter.visibility = View.VISIBLE
                                                    }
                                                    Log.d(TAG, "count-->" + count)
                                                } else {
                                                    count = 0
                                                    Log.d(TAG, "count-->" + count)
                                                    fragmentProductDetailsBinding.tvCounter.text = count.toString()
                                                    fragmentProductDetailsBinding.btnAddtoproduct.visibility = View.VISIBLE
                                                    fragmentProductDetailsBinding.llcounter.visibility = View.GONE

                                                }
                                            }
                                        }else{
                                            count = 0
                                            fragmentProductDetailsBinding.tvCounter.text = count.toString()
                                        }


                                        if (itResponse.totalCount==0){
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible = false
                                        }else{
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible = true
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).number = itResponse.totalCount
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).backgroundColor = Color.parseColor("#E63425")

                                        }


                                    } else {
                                        viewModel.cartListItem.value = 0
                                        advanceisadded= false

                                        if (itResponse!!.totalCount==0){
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible = false
                                        }else{
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible = true
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).number = itResponse.totalCount
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).backgroundColor = Color.parseColor("#E63425")

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


    private fun DeleteCustomerCart() {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.DeleteCustomerCart(
                DeleteCustomerCartRequest(
                    customerId = Shared_Preferences.getUserId(),
                    productMainCategoryId = Shared_Preferences.getMaincatid().toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                if (resource.data?.status == true) {
                                    getProductDetails()
                                    getAddressList()


                                } else {

                                    Toast.makeText(
                                        mainActivity,
                                        resource.data?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

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


    private fun onUpdate(count:Int, type:String,  clickType:Int){

        try {

            when (clickType) {
                0 -> {


                    if (count<1) {
                        productDeleteFromCart(cartid)
//                        Toast.makeText(mainActivity, "Delete cart", Toast.LENGTH_SHORT).show()

                    } else {

//                        Toast.makeText(mainActivity, "Update Decrease", Toast.LENGTH_SHORT).show()

                        updateCart(
                            discount,
                            productId,
                            fragmentProductDetailsBinding.tvProductName.text.toString(),
                            count.toString(),
                            price,
                            unitid,
                            unitname,
                            cartid
                        )
                    }
//                productaddtoCart(cartData.urc_product_id.toString(),false)
                }

                1 -> {
                    if (count==1) {
//                    Toast.makeText(mainActivity, "Add to cart", Toast.LENGTH_SHORT).show()
                        productaddtoCart()

                    }else if (count>1){

//                    Toast.makeText(mainActivity, "Update Increase", Toast.LENGTH_SHORT).show()

                        updateCart(
                            discount,
                            productId,
                            fragmentProductDetailsBinding.tvProductName.text.toString(),
                            count.toString(),
                            price,
                            unitid,
                            unitname,
                            cartid
                        )

                    }

//                productaddtoCart(cartData.urc_product_id.toString(),true)
                }

            }


        }catch (e:Exception){

        }


    }


    private fun productDeleteFromCart(id: String) {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.deletecart(
                DeleteCartRequest(
                    id = id
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                if (resource.data?.status == true) {
                                    CartList(true)
                                    getProductDetails()
                                    getAddressList()

                                } else {

                                    Toast.makeText(
                                        mainActivity,
                                        resource.data?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

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


    private fun updateCart(
        discount: String,
        productid: String,
        productname: String,
        qty: String,
        price: String,
        unitid: String,
        unitname: String,
        cartid: String
    ) {

        Log.d(TAG, "count-->" + qty)

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.updatecart(
                CartUpdateRequest(
                    customerId = Shared_Preferences.getUserId(),
                    productMainCategoryId = Shared_Preferences.getMaincatid().toString(),
                    isAdvanceOrderRequest = false,
                    customerName = Shared_Preferences.getName().toString(),
                    discount = discount,
                    discountPercentage = discount,
                    id = cartid,
                    productId = productid,
                    productName = productname,
                    quantity = qty,
                    taxValue = "0",
                    total = (price.toDouble().times(qty.toDouble())).toString(),
                    unitId = unitid,
                    unitName = unitname,
                    unitPrice = price
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                if (resource.data?.status == true) {
                                    CartList(true)
                                    getProductDetails()
                                    getAddressList()


                                } else {

                                    Toast.makeText(
                                        mainActivity,
                                        resource.data?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

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


    private fun addtoWishlist(customerid:String, productid:String, view: View){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.addtowishlist(WishListAddRequest(customerId = customerid, productId = productid))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                if (resource.data?.status == true) {
                                    val builder = android.app.AlertDialog.Builder(mainActivity)
                                    builder.setMessage(resource.data.message)
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        dialog.dismiss()
                                        val navController = Navigation.findNavController(view)
                                        navController.navigate(R.id.nav_wishlist)
                                    }
                                    val alert = builder.create()
                                    alert.setOnShowListener { arg0 ->
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                            .setTextColor(resources.getColor(R.color.orange))
                                    }
                                    alert.show()

                                } else {

                                    Toast.makeText(mainActivity, resource.data?.message, Toast.LENGTH_SHORT).show()

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
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show() }

    }



    override fun onClick(position: Int, view: View, id: String) {
        val bundle = Bundle()
        bundle.putString("productid", id)
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_productdetails, bundle)
    }

}