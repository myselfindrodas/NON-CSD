package com.grocery.sainik_grocery.ui.fragment

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.addtocartmodel.AddtocartRequest
import com.grocery.sainik_grocery.data.model.deletecartmodel.DeleteCartRequest
import com.grocery.sainik_grocery.data.model.deletefullcartmodel.DeleteCustomerCartRequest
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartData
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartListRequest
import com.grocery.sainik_grocery.data.model.productlistmodel.Data
import com.grocery.sainik_grocery.data.model.productlistmodel.ProductListRequest
import com.grocery.sainik_grocery.data.model.updatecartmodel.CartUpdateRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentPartyProductListBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.PartyProductAdapter
import com.grocery.sainik_grocery.utils.ItemOffsetDecoration
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel


class PartyProductListFragment : Fragment(), PartyProductAdapter.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel
    lateinit var binding: FragmentPartyProductListBinding
    private var partyProductAdapter: PartyProductAdapter? = null
    var productArrayList: ArrayList<Data> = arrayListOf()
    var cartArrayList: ArrayList<CartData> = arrayListOf()
    private var qtyText: TextView? = null
    private var priceText: TextView? = null

    var category = ""
    var categoryName = ""
    var qty = ""

    var mIsLoading = false;
    var mIsLastPage = false;
    var mCurrentPage = 0;
    var pageSize = 15;

    companion object {
        var sortType: String = "popularity"
        var keyword: String = ""

        var attribute_id = ArrayList<Int>()
        var filterPrice: String = ""
        var catId = ""
        var catName = ""

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_party_product_list,
                container,
                false
            )
        val root = binding.root
        mainActivity = activity as MainActivity

//        val intent = arguments
//        if (intent != null && intent.containsKey("viewalltype")) {
//            category = intent.getString("viewalltype", "")
//            if (category == "categoryProduct") {
//                ProductListFragment.categoryId = intent.getString("catId", "")
//                categoryName = intent.getString("catName", "")
//            }
//        }


        val intent = arguments
        if (intent != null && intent.containsKey("catName")) {
            catName = intent.getString("catName","")
        }

        if (intent != null && intent.containsKey("catId")) {
            catId = intent.getString("catId","")
        }


        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topnav.tvNavtitle.text = catName


        binding.topnav.btnBack.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


//        binding.topnav.etSearch.setOnClickListener {
//
//            val navController = Navigation.findNavController(it)
//            navController.navigate(R.id.nav_searchorder)
//        }


        mainActivity.setBottomNavigationVisibility(true)



        partyProductAdapter = PartyProductAdapter(mainActivity, this@PartyProductListFragment)
        binding.rvProductList.adapter = partyProductAdapter
        binding.rvProductList.layoutManager =
            GridLayoutManager(mainActivity, 1)
        binding.rvProductList.addOnScrollListener(recyclerOnScroll)
        val itemDecoration = ItemOffsetDecoration(mainActivity, R.dimen.photos_list_spacing1)
        binding.rvProductList.addItemDecoration(itemDecoration)
        mCurrentPage=0
        getAllProductList(true)


    }


    private fun getAllProductList(isFirstPage: Boolean) {
        if (Utilities.isNetworkAvailable(mainActivity)) {

            mIsLoading = true
//            if (isFirstPage)
//                mCurrentPage = 0
//            else
            mCurrentPage += 1


            viewModel.productList(
                ProductListRequest("25", mCurrentPage.toString(), catId)
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (resource.data?.status == true) {
                                resource.data?.let { itProfileInfo ->
                                    productArrayList.clear()
                                    partyProductAdapter?.notifyDataSetChanged()
                                    productArrayList.addAll(itProfileInfo.data)
                                    partyProductAdapter?.notifyItemRangeInserted(0, itProfileInfo.totalCount)

                                    mIsLoading = false
                                    mIsLastPage = mCurrentPage == itProfileInfo.totalPages
                                    pageSize = itProfileInfo.totalPages

                                    if (itProfileInfo.data.isEmpty()) {

                                        binding.nodata.root.visibility =
                                            View.VISIBLE
                                    } else {

                                        binding.nodata.root.visibility =
                                            View.GONE

                                    }
                                }
                            } else {

                                if (resource.data?.data!!.isEmpty()) {

                                    binding.nodata.root.visibility = View.VISIBLE
                                } else {

                                    binding.nodata.root.visibility = View.GONE

                                }
//                                fragmentProductListBinding.nodata.root.visibility = View.VISIBLE
                            }
                            mainActivity.hideProgressDialog()
                            CartList(isFirstPage)


                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)

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




                                        if (itResponse.data[0].isAdvanceOrderRequest == false) {

                                            val builder = AlertDialog.Builder(mainActivity)
                                            builder.setCancelable(false)
                                            builder.setMessage(
                                                "You Have Some of regular product already added inside cart!" +
                                                        " Please Delete previous cart items for advance product add"
                                            )
                                            builder.setPositiveButton(
                                                "Ok"
                                            ) { dialog, which ->

                                                DeleteCustomerCart()
                                                dialog.cancel()
                                            }

                                            builder.setNegativeButton("Cancel") { dialog, which ->
                                                val intent =
                                                    Intent(mainActivity, MainActivity::class.java)
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


                                        }else{


                                            cartArrayList.clear()
                                            cartArrayList.addAll(itResponse.data)

                                            if (itResponse.totalCount == 0) {
                                                mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible =
                                                    false
                                            } else {
                                                mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible =
                                                    true
                                                mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).number =
                                                    itResponse.totalCount
                                                mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).backgroundColor =
                                                    Color.parseColor("#E63425")

                                            }

                                        }




//                                        itResponse.let {itItem->
//                                            if (itItem.data.isNullOrEmpty()) {
////                                                viewModel.cartListItem.value = 0
////                                                cartAdapter?.updateData(arrayListOf(), "")
//                                                fragmentCartBinding.nodata.root.visibility=View.VISIBLE
//                                            }else{
//                                                fragmentCartBinding.nodata.root.visibility=View.GONE
//                                                fragmentCartBinding.btnGo.visibility=View.VISIBLE
//                                                fragmentCartBinding.llTotalPice.visibility=View.VISIBLE
//                                                cartAdapter?.updateData(itItem.data)
//                                            }
//
//                                            var totalPrice= 0
//                                            itItem.data.forEach { item->
//                                                totalPrice += item.total.roundToInt()
//                                            }
//
//                                            fragmentCartBinding.tvItemprice.text= "₹ $totalPrice"
//                                            fragmentCartBinding.tvItemQty.text= "${itItem.data.size} Items"
//                                        }
//                                        viewModel.cartListItem.value= itResponse.data.cart.size


                                    } else {
                                        cartArrayList.clear()
                                        viewModel.cartListItem.value = 0

                                        if (itResponse!!.totalCount == 0) {
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible =
                                                false
                                        } else {
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible =
                                                true
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).number =
                                                itResponse.totalCount
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).backgroundColor =
                                                Color.parseColor("#E63425")

                                        }
//                                        cartAdapter?.updateData(arrayListOf())
////                                        Toast.makeText(
////                                            mainActivity,
////                                            resource.data?.message,
////                                            Toast.LENGTH_SHORT
////                                        ).show()
//
//                                        fragmentCartBinding.nodata.root.visibility=View.VISIBLE
//                                        fragmentCartBinding.btnGo.visibility=View.GONE
//                                        fragmentCartBinding.llTotalPice.visibility=View.GONE
                                    }

                                    if (isFirstPage) partyProductAdapter?.updateData(
                                        productArrayList,
                                        cartArrayList
                                    ) else partyProductAdapter?.addData(
                                        productArrayList,
                                        cartArrayList
                                    )
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


    val recyclerOnScroll = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            // number of visible items
            val visibleItemCount = recyclerView.layoutManager?.childCount;
            // number of items in layout
            val totalItemCount = recyclerView.layoutManager?.itemCount;
            // the position of first visible item
            val firstVisibleItemPosition =
                (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

            val isNotLoadingAndNotLastPage = !mIsLoading && !mIsLastPage;
            // flag if number of visible items is at the last
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount!! >= totalItemCount!!;
            // validate non negative values
            val isValidFirstItem = firstVisibleItemPosition >= 0;
            // validate total items are more than possible visible items
            val totalIsMoreThanVisible = totalItemCount >= pageSize;
            // flag to know whether to load more
            val shouldLoadMore =
                isValidFirstItem && isAtLastItem && totalIsMoreThanVisible && isNotLoadingAndNotLastPage

            if (shouldLoadMore) getAllProductList(false)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mIsLoading = false
        mIsLastPage = false
        mCurrentPage = 0
        ProductListFragment.sortType = "popularity"
        ProductListFragment.keyword = ""
        ProductListFragment.filterPrice = ""
        ProductListFragment.filterPrice = ""
        ProductListFragment.attribute_id = ArrayList<Int>()
    }


    override fun onClick(
        position: Int,
        view: View,
        tvQty: TextView,
        tvPrice: TextView,
        str: String,
        id: String,
        prodcutlist: Data
    ) {

        if (str == "ivImg") {
            val bundle = Bundle()
            bundle.putString("productid", id)
            bundle.putString("type", "partyadd")
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.nav_productdetails, bundle)

        } else if (str == "addtocart") {

            productaddtoCart(
                prodcutlist.discount.toString(),
                prodcutlist.id,
                prodcutlist.name,
                prodcutlist.salesPrice.toString(),
                prodcutlist.unitId,
                prodcutlist.unitName
            )

        }

        qtyText = tvQty
        priceText = tvPrice


    }

    override fun onUpdate(
        position: Int,
        view: View,
        count: Int,
        tvPrice: TextView,
        id: String,
        cartid: String,
        prodcutlist: Data,
        type: String,
        clicktype: Int
    ) {
        Log.d(ContentValues.TAG, "qty-->" + count)
        Log.d(ContentValues.TAG, "cartid-->" + cartid)

        try {

            when (clicktype) {
                0 -> {


                    if (count < 1) {
                        productDeleteFromCart(cartid)
//                        Toast.makeText(mainActivity, "Delete cart", Toast.LENGTH_SHORT).show()

                    } else {

//                        Toast.makeText(mainActivity, "Update Decrease", Toast.LENGTH_SHORT).show()

                        updateCart(
                            prodcutlist.discount.toString(),
                            prodcutlist.id,
                            prodcutlist.name,
                            count.toString(),
                            prodcutlist.salesPrice.toString(),
                            prodcutlist.unitId,
                            prodcutlist.unitName,
                            cartid
                        )
                    }
                }

                1 -> {
                    if (count == 1) {
//                    Toast.makeText(mainActivity, "Add to cart", Toast.LENGTH_SHORT).show()

                        productaddtoCart(
                            prodcutlist.discount.toString(),
                            prodcutlist.id,
                            prodcutlist.name,
                            prodcutlist.salesPrice.toString(),
                            prodcutlist.unitId,
                            prodcutlist.unitName
                        )
                    } else if (count > 1) {

//                    Toast.makeText(mainActivity, "Update Increase", Toast.LENGTH_SHORT).show()

                        updateCart(
                            prodcutlist.discount.toString(),
                            prodcutlist.id,
                            prodcutlist.name,
                            count.toString(),
                            prodcutlist.salesPrice.toString(),
                            prodcutlist.unitId,
                            prodcutlist.unitName,
                            cartid
                        )

                    }

                }

            }

        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "error-->" + e)
        }

    }


    private fun productaddtoCart(
        discount: String,
        productid: String,
        prodcutname: String,
        price: String,
        unitid: String,
        unitname: String
    ) {
        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.AddToCart(
                AddtocartRequest(
                    customerId = Shared_Preferences.getUserId(),
                    productMainCategoryId = Shared_Preferences.getMaincatid().toString(),
                    isAdvanceOrderRequest = true,
                    customerName = Shared_Preferences.getName().toString(),
                    discount = discount,
                    discountPercentage = discount,
                    productId = productid,
                    productName = prodcutname,
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


                                    CartList(true)
//                                    val builder = android.app.AlertDialog.Builder(mainActivity)
//                                    builder.setMessage(resource.data.message)
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
////                                        val navController = Navigation.findNavController(fragmentProductListBinding.root)
////                                        navController.navigate(R.id.nav_cart)
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
//                                    getAllProductList(true)
//                                    productCartList()


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
                                    CartList(true)
//                                    getAllProductList(true)


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

        Log.d(ContentValues.TAG, "count-->" + qty)

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.updatecart(
                CartUpdateRequest(
                    customerId = Shared_Preferences.getUserId(),
                    productMainCategoryId = Shared_Preferences.getMaincatid().toString(),
                    isAdvanceOrderRequest = true,
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
//                                getAllProductList(true)

//                                    Toast.makeText(
//                                        mainActivity,
//                                        resource.data.message,
//                                        Toast.LENGTH_SHORT
//                                    ).show()


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

}