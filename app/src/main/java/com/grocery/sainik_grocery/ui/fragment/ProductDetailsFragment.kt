package com.grocery.sainik_grocery.ui.fragment

import android.content.ContentValues
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
import com.grocery.sainik_grocery.data.model.productdetailsmodel.ProductDetailsRequest
import com.grocery.sainik_grocery.data.model.productlistmodel.ProductListRequest
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
    private var imageURL: String="https://sainik.shyamfuture.in/"

    private lateinit var viewModel: CommonViewModel
    private var productId = ""
    private var urc_productId = ""
    private var wishlistId = ""
    private var discount = ""
    private var price = ""
    private var unitid = ""
    private var unitname = ""
    private var unitprice = ""


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

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm


        return root
    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = RelatedProductAdapter(mainActivity, this@ProductDetailsFragment)
        fragmentProductDetailsBinding.rvSimilarproducts.adapter = productAdapter
        fragmentProductDetailsBinding.rvSimilarproducts.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
//        val productList = java.util.ArrayList<DataProductList>()
//        for (i in 1..5){
//            productList.add(DataProductList(60.6,60.5,1, 0, "salt", 25.4, "kg", 1))
//        }
//        productAdapter!!.updateData(productList, "R.drawable.item")

//        productItemAdapter = ProductItemAdapter(mainActivity, this@ProductDetailsFragment)
//        fragmentProductDetailsBinding.rvItemImg.adapter = productItemAdapter
//        fragmentProductDetailsBinding.rvItemImg.layoutManager =
//            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
//        val productItemList = java.util.ArrayList<ProductImage>()
//        for (i in 1..5){
//            productItemList.add(ProductImage(1,"wqe", 2))
//        }
//        productItemAdapter!!.updateData(productItemList, "R.drawable.item")

//        packSizeAdapter = PackSizeAdapter(mainActivity, this@ProductDetailsFragment)
//        fragmentProductDetailsBinding.rvPackSize.adapter = packSizeAdapter
//        fragmentProductDetailsBinding.rvPackSize.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
//        val packSizeList = java.util.ArrayList<ProductPackSize>()
//        for (i in 1..5){
//            packSizeList.add(ProductPackSize(1,45.5, 40.5, " 500gm", 2, 1))
//        }
//        packSizeAdapter!!.updateData(packSizeList)

        fragmentProductDetailsBinding.topnav.btnBack.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


//        viewModel.cartListItem.observe(viewLifecycleOwner, Observer {count->
//            if (count>0) {
//                fragmentProductDetailsBinding.topnav.tvCartCount.text = count.toString()
//                fragmentProductDetailsBinding.topnav.cvCartCount.visibility=View.VISIBLE
//            }else{
//                fragmentProductDetailsBinding.topnav.cvCartCount.visibility=View.GONE
//            }
//
//            HomeFragment.cartCount=count
//        })

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

            productaddtoCart()

        }



        fragmentProductDetailsBinding.btnAddWishlist.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            addtoWishlist(Shared_Preferences.getUserId(), productId, it)

        }

        getProductDetails()

//        setProductDetailsView(fragmentProductDetailsBinding)

//        productCartList()

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


    //    private fun setProductDetailsView(binding: FragmentProductDetailsBinding) {
//
//        with(binding) {
//
//            btnaboutproductExpand.setOnClickListener {
//
//                btnaboutproductExpand.visibility = View.GONE
//                btnaboutproductClose.visibility = View.VISIBLE
//                llaboutproduct.visibility = View.VISIBLE
//
//                btnbenifitproductExpand.visibility = View.VISIBLE
//                btnbenifitproductClose.visibility = View.GONE
//                llbenifits.visibility = View.GONE
//
//
//                btnusesExpand.visibility = View.VISIBLE
//                btnusesClose.visibility = View.GONE
//                llusages.visibility = View.GONE
//            }
//
//
//            btnaboutproductClose.setOnClickListener {
//
//                btnaboutproductExpand.visibility = View.VISIBLE
//                btnaboutproductClose.visibility = View.GONE
//                llaboutproduct.visibility = View.GONE
//            }
//
//
//
//
//            btnbenifitproductExpand.setOnClickListener {
//
//                btnbenifitproductExpand.visibility = View.GONE
//                btnbenifitproductClose.visibility = View.VISIBLE
//                llbenifits.visibility = View.VISIBLE
//
//                btnaboutproductExpand.visibility = View.VISIBLE
//                btnaboutproductClose.visibility = View.GONE
//                llaboutproduct.visibility = View.GONE
//
//                btnusesExpand.visibility = View.VISIBLE
//                btnusesClose.visibility = View.GONE
//                llusages.visibility = View.GONE
//            }
//
//
//            btnbenifitproductClose.setOnClickListener {
//
//                btnbenifitproductExpand.visibility = View.VISIBLE
//                btnbenifitproductClose.visibility = View.GONE
//                llbenifits.visibility = View.GONE
//            }
//
//
//
//            btnusesExpand.setOnClickListener {
//
//                btnusesExpand.visibility = View.GONE
//                btnusesClose.visibility = View.VISIBLE
//                llusages.visibility = View.VISIBLE
//
//
//                btnbenifitproductExpand.visibility = View.VISIBLE
//                btnbenifitproductClose.visibility = View.GONE
//                llbenifits.visibility = View.GONE
//
//
//                btnaboutproductExpand.visibility = View.VISIBLE
//                btnaboutproductClose.visibility = View.GONE
//                llaboutproduct.visibility = View.GONE
//
//            }
//
//
//            btnusesClose.setOnClickListener {
//
//                btnusesExpand.visibility = View.VISIBLE
//                btnusesClose.visibility = View.GONE
//                llusages.visibility = View.GONE
//            }
//        }
//    }




//
//    private fun getAddressList() {
//
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//
//            viewModel.addressList()
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                resource.data?.let {itResponse->
//
//                                    if (itResponse.status) {
//                                        itResponse.data.address.forEach {item->
//                                            if (item.isPrimary==1){
//                                                fragmentProductDetailsBinding.tvAddress.text= item.apartmentName + " " +item.houseNo +
//                                                        " " + item.streetDetails + "\n" +
//                                                        item.city + ", " + item.pincode
//                                            }
//                                        }
//
//                                       // addressAdapter?.updateData(itResponse.data.address)
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
                                            .error(R.drawable.login_img)
//                                        .placeholder(R.drawable.loader_gif)
                                            .into(fragmentProductDetailsBinding.ivProductmain)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
//                                productItemAdapter?.updateData(itProfileInfo.data.productDetails.product.productImages,itProfileInfo.productImageUrl)
                                    //packSizeAdapter?.updateData(itProfileInfo.data.productDetails.productPackSize)

//                                productAdapter?.updateData(itProfileInfo.data.similarProducts as ArrayList<DataProductList>,itProfileInfo.productImageUrl)
                                    with(fragmentProductDetailsBinding) {

//                                    if (itProfileInfo.data.productDetails.productPackSize.isNullOrEmpty()) {
//                                        llPackTxt.visibility = View.GONE
//                                    } else {
//                                        llPackTxt.visibility = View.VISIBLE
//                                    }
                                        tvProductName.setText(itProfileInfo.data.name)
                                        // tvProductPrice.setText("Price: ₹ ${itProfileInfo.data.productDetails.sellingPrice.toString()}")
//                                    llaboutproduct.setText(itProfileInfo.data.productDetails.product.about)
//                                    llbenifits.setText(itProfileInfo.data.productDetails.product.benifits)
//                                    llusages.setText(itProfileInfo.data.productDetails.product.storageAndUses)
                                        if (itProfileInfo.data.discount > 0) {
                                            tvDiscount.text = "${itProfileInfo.data.discount}% Off"
                                            discount = itProfileInfo.data.discount.toString()
                                            llDiscount.visibility = View.VISIBLE
                                            tvPriceOld.visibility = View.VISIBLE
                                        } else {
                                            llDiscount.visibility = View.GONE
                                            tvPriceOld.visibility = View.GONE
                                            discount = "0"
                                        }


                                        tvPrice.text =
                                            " ₹ ${itProfileInfo.data.salesPrice.toString()}/${itProfileInfo.data.unitName ?: ""}"
                                        tvPriceOld.text =
                                            "₹ ${itProfileInfo.data.mrp.toString()}/${itProfileInfo.data.unitName ?: ""}"

                                        tvPriceOld.paintFlags =
                                            tvPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                                        urc_productId = itProfileInfo.data.id
                                        wishlistId = itProfileInfo.data.id
                                        price = itProfileInfo.data.salesPrice.toString()
                                        unitid = itProfileInfo.data.unitId.toString()
                                        unitname = itProfileInfo.data.unitName.toString()
//                                    rateProduct.rating = itProfileInfo.data.productDetails.avgRating.toFloat()
                                        rateProduct.rating = 5.0F
                                    }
                                    getSimilarproduct()

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
        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.AddToCart(
                AddtocartRequest(
                    customerId = Shared_Preferences.getUserId(),
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
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                if (resource.data?.status == true) {

//                                    productCartList()
                                    val builder = android.app.AlertDialog.Builder(mainActivity)
                                    builder.setMessage(resource.data.message)
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
                                        val navController = Navigation.findNavController(fragmentProductDetailsBinding.root)
                                        navController.navigate(R.id.nav_cart)
                                        dialog.dismiss()
                                    }
                                    val alert = builder.create()
                                    alert.setOnShowListener { arg0 ->
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                            .setTextColor(resources.getColor(R.color.orange))
                                    }
                                    alert.show()

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

//
//    private fun productaddtoCart(view: View, packSizeId: String, mIsAdded: Boolean) {
//
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            val cartRequestModel = if (mIsAdded) {
//                CartRequestModel(
//                    urc_product_id = packSizeId,
//                    is_added = "1"
//                )
//            } else {
//                CartRequestModel(
//                    urc_product_id = packSizeId,
//                    is_added = "0"
//                )
//            }
//            viewModel.cartadd(
//                cartRequestModel
//            )
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                if (resource.data?.status == true) {
//
//                                    productCartList()
//                                    val builder = android.app.AlertDialog.Builder(mainActivity)
//                                    builder.setMessage(resource.data.message)
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        /*val navController = Navigation.findNavController(view)
//                                        navController.navigate(R.id.nav_cart)*/
//                                        dialog.dismiss()
//                                    }
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0 ->
//                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.orange))
//                                    }
//                                    alert.show()
//
//                                } else {
//
//                                    Toast.makeText(
//                                        mainActivity,
//                                        resource.data?.message,
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//
//                                }
//
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
//
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
//
//
//    private fun productCartList() {
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            viewModel.cartList()
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                resource.data.let {itResponse->
//
//                                    if (itResponse?.status == true) {
//
//                                        if (itResponse.data.cart.isNullOrEmpty()) {
//                                            viewModel.cartListItem.value = 0
//                                        }else
//                                        viewModel.cartListItem.value = itResponse.data.cart.size
//
//                                    } else {
//
//                                        viewModel.cartListItem.value=0
//                                        /*Toast.makeText(
//                                            mainActivity,
//                                            resource.data?.message,
//                                            Toast.LENGTH_SHORT
//                                        ).show()*/
//
//                                    }
//
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


//    override fun onImgItemClick(position: Int, view: View, imgUrl: String) {
//
//        try {
//
//            Picasso.get()
//                .load(imgUrl)
//                .error(R.drawable.login_img)
////                .placeholder(R.drawable.loader_gif)
//                .into(fragmentProductDetailsBinding.ivProductmain)
//        }catch (e:Exception){
//            e.printStackTrace()
//        }
//
//    }
//
//    override fun onPackClick(position: Int, view: View) {
//
//
//    }

//    override fun onClick(position: Int, view: View, tvQty: TextView, tvPrice: TextView, str: String, id: Int){
//
//
//
//    }


    override fun onClick(position: Int, view: View, id: String) {
        val bundle = Bundle()
        bundle.putString("productid", id)
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_productdetails, bundle)
    }

}