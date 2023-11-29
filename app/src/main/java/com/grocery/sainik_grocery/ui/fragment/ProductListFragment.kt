package com.grocery.sainik_grocery.ui.fragment

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.QtyAlertListData
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.addtocartmodel.AddtocartRequest
import com.grocery.sainik_grocery.data.model.deletecartmodel.DeleteCartRequest
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartData
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartListRequest
import com.grocery.sainik_grocery.data.model.productlistmodel.Data
import com.grocery.sainik_grocery.data.model.productlistmodel.ProductListRequest
import com.grocery.sainik_grocery.data.model.updatecartmodel.CartUpdateRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentProductListBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.CategoryListAdapter
import com.grocery.sainik_grocery.ui.adapter.HorizontalSubCategoryAdapter
import com.grocery.sainik_grocery.ui.adapter.ProductAdapter
import com.grocery.sainik_grocery.ui.adapter.QtyAlertListAdapter
import com.grocery.sainik_grocery.ui.adapter.SubCategoryListAdapter
import com.grocery.sainik_grocery.utils.ItemOffsetDecoration
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import java.util.Locale
import java.util.Objects

class ProductListFragment : Fragment(), ProductAdapter.OnItemClickListener,
    CategoryListAdapter.OnItemClickListener, SubCategoryListAdapter.SubCategoryOnItemClickListener,
    QtyAlertListAdapter.OnItemClickListener,
    HorizontalSubCategoryAdapter.HorizontalSubCategoryOnItemClickListener {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentProductListBinding: FragmentProductListBinding
    private var productAdapter: ProductAdapter? = null
    private var categoryListAdapter: CategoryListAdapter? = null
    private var subCategoryListAdapter: SubCategoryListAdapter? = null
    private var horizontalSubCategoryAdapter: HorizontalSubCategoryAdapter? = null
    private var qtyAlertListAdapter: QtyAlertListAdapter? = null
    private var qtyText: TextView? = null
    private var priceText: TextView? = null
    var alert: AlertDialog? = null
    private val REQUEST_CODE_SPEECH_INPUT = 1


    var category = ""
    var categoryName = ""
    var qty = ""

    //    val categoryModelArrayList: ArrayList<Category> = arrayListOf()
    var productArrayList: ArrayList<Data> = arrayListOf()
    var cartArrayList: ArrayList<CartData> = arrayListOf()
    private lateinit var viewModel: CommonViewModel

    var mIsLoading = false;
    var mIsLastPage = false;
    var mCurrentPage = 0;
    var pageSize = 10;

    companion object {
        var sortType: String = "popularity"
        var keyword: String = ""

        var attribute_id = ArrayList<Int>()
        var filterPrice: String = ""
        var categoryId = ""

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentProductListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        val root = fragmentProductListBinding.root
        mainActivity = activity as MainActivity

        val intent = arguments
        if (intent != null && intent.containsKey("viewalltype")) {
            category = intent.getString("viewalltype", "")
            if (category == "categoryProduct") {
                categoryId = intent.getString("catId", "")
                categoryName = intent.getString("catName", "")
            }
        }

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        println("SORT TYPE   $sortType")
        viewModel = vm



        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        horizontalSubCategoryAdapter =
            HorizontalSubCategoryAdapter(mainActivity, this@ProductListFragment)
        fragmentProductListBinding.rvHorizontalSubCategory.adapter = horizontalSubCategoryAdapter
        fragmentProductListBinding.rvHorizontalSubCategory.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
//        getAllcategory(Shared_Preferences.getUrcid().toString())
//        val horizontalcategoryList = ArrayList<Category>()
//        for (i in 1..5) {
//            horizontalcategoryList.add(Category(2, "", "cleaning"))
//        }
//        horizontalSubCategoryAdapter!!.updateData(horizontalcategoryList)

        fragmentProductListBinding.topnav.btnBack.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }
        if (HomeFragment.cartCount > 0) {
            fragmentProductListBinding.topnav.tvCartCount.text = HomeFragment.cartCount.toString()
            fragmentProductListBinding.topnav.cvCartCount.visibility = View.VISIBLE
        } else {
            fragmentProductListBinding.topnav.cvCartCount.visibility = View.GONE
        }
        /* productCartList()

         viewModel.cartListItem.observe(viewLifecycleOwner, Observer {count->

         })*/
        if (category == "category") {
            mainActivity.setBottomNavigationVisibility(true)
            fragmentProductListBinding.rvHorizontalSubCategory.visibility = View.GONE
            fragmentProductListBinding.topnav.tvNavtitle.text = categoryName
//            fragmentProductListBinding.tvsubtitle.text = "Category List"
            fragmentProductListBinding.tvsubtitle.visibility = View.GONE
            fragmentProductListBinding.llfilersort.visibility = View.GONE
            fragmentProductListBinding.topnav.btnSearch.visibility = View.VISIBLE
            categoryListAdapter = CategoryListAdapter(mainActivity, this@ProductListFragment)
            fragmentProductListBinding.rvProductList.adapter = categoryListAdapter
            fragmentProductListBinding.rvProductList.layoutManager =
                GridLayoutManager(mainActivity, 1)
            val itemDecoration = ItemOffsetDecoration(mainActivity, R.dimen.photos_list_spacing1)
            fragmentProductListBinding.rvProductList.addItemDecoration(itemDecoration)
            categorylist()
//            getAllProductList(true)
//            getAllcategory(Shared_Preferences.getUrcid().toString())
//            val categoryList = ArrayList<Category>()
//            for (i in 1..5) {
//                categoryList.add(Category(2, "", "cleaning"))
//            }
//            categoryListAdapter!!.updateData(categoryList, "R.drawable.item")

        } else {
            mainActivity.setBottomNavigationVisibility(true)
            fragmentProductListBinding.topnav.tvNavtitle.text = categoryName
            fragmentProductListBinding.llfilersort.visibility = View.VISIBLE
            fragmentProductListBinding.topnav.btnSearch.visibility = View.VISIBLE

            productAdapter = ProductAdapter(mainActivity, this@ProductListFragment)
            fragmentProductListBinding.rvProductList.adapter = productAdapter
            fragmentProductListBinding.rvProductList.layoutManager =
                GridLayoutManager(mainActivity, 1)
            fragmentProductListBinding.rvProductList.addOnScrollListener(recyclerOnScroll)
            //productAdapter?.updateData(categoryModelArrayList)
            val itemDecoration = ItemOffsetDecoration(mainActivity, R.dimen.photos_list_spacing1)
            fragmentProductListBinding.rvProductList.addItemDecoration(itemDecoration)
            getAllProductList(true)

//            getAllProdutList(true, Shared_Preferences.getUrcid().toString())
//            val productList = ArrayList<DataProductList>()
//            for (i in 1..5) {
//                productList.add(DataProductList(60.6, 60.5, 1, 0, "salt", 25.4, "kg", 1))
//            }
//            productAdapter!!.updateData(productList, "R.drawable.item")

            fragmentProductListBinding.topnav.ivMicProductList.setOnClickListener {

                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )

                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
                )

                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something...")

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
                } catch (e: Exception) {
                    Toast
                        .makeText(
                            mainActivity, " " + e.message,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            }

            fragmentProductListBinding.topnav.etSearchProductList.addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {


                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if (s?.length!! > 1) {
                        keyword = s.toString()
                        //  getAllProdutList(true, Shared_Preferences.getUrcid().toString())
                    } else {
                        keyword = ""
                        // getAllProdutList(true, Shared_Preferences.getUrcid().toString())
                    }

//                    getAllProdutList(true, Shared_Preferences.getUrcid().toString())
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })


        }


        fragmentProductListBinding.btnSort.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_sort_by)
        }

        fragmentProductListBinding.topnav.clCart.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)
        }

        fragmentProductListBinding.topnav.ivNotification.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)
        }


        fragmentProductListBinding.topnav.btnWishlist.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wishlist)
        }

        fragmentProductListBinding.btnFilter.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_filter_by)
        }

        categorylist()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                fragmentProductListBinding.topnav.etSearchProductList.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }

    private fun getAllProductList(isFirstPage: Boolean) {
        if (Utilities.isNetworkAvailable(mainActivity)) {

            mIsLoading = true
//            if (isFirstPage)
//                mCurrentPage = 0
//            else
            mCurrentPage += 1


            viewModel.productList(
                ProductListRequest("25", "0", categoryId)
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (resource.data?.status == true) {
                                resource.data?.let { itProfileInfo ->
                                    productArrayList.clear()
                                    productArrayList.addAll(itProfileInfo.data)

                                    mIsLoading = false
                                    mIsLastPage =
                                        mCurrentPage == itProfileInfo.totalCount
                                    pageSize = itProfileInfo.pageSize
                                    fragmentProductListBinding.tvsubtitle.text =
                                        " ${itProfileInfo.data.size} products"

                                    if (itProfileInfo.data.isEmpty()) {

                                        fragmentProductListBinding.nodata.root.visibility =
                                            View.VISIBLE
                                    } else {

                                        fragmentProductListBinding.nodata.root.visibility =
                                            View.GONE

                                    }
                                }
                            } else {
//                                fragmentProductListBinding.nodata.root.visibility = View.VISIBLE
                            }
                            mainActivity.hideProgressDialog()
                            CartList(isFirstPage)


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


    private fun categorylist() {
        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.categorylist()
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data.let { itResponse ->

                                    if (itResponse?.status == true) {

                                        horizontalSubCategoryAdapter!!.updateData(itResponse.data)
                                        categoryListAdapter!!.updateData(itResponse.data)

                                    }

                                }

                                mainActivity.hideProgressDialog()
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

//    private fun getAllcategory(urcid: String) {
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            viewModel.categoryall(
//                CategoryRequestModel(urc_id = urcid)
//            ).observe(mainActivity) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            mainActivity.hideProgressDialog()
//                            resource.data?.let { itProfileInfo ->
//
//
//                                categoryListAdapter?.updateData(
//                                    itProfileInfo.data.category as ArrayList<Category>,
//                                    itProfileInfo.categoryImageUrl
//                                )
//
//                                subCategoryListAdapter?.updateData(
//                                    itProfileInfo.data.category as ArrayList<Category>
//                                )
//
//                                horizontalSubCategoryAdapter?.updateData(
//                                    itProfileInfo.data.category as ArrayList<Category>
//                                )
//
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
//            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
//
//        }
//
//    }

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

//    private fun getAllProdutList(isFirstPage: Boolean, urcid: String) {
//        fragmentProductListBinding.nodata.root.visibility = View.GONE
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            mIsLoading = true
//            if (isFirstPage)
//                mCurrentPage = 1
//            else
//                mCurrentPage += 1
//
//
//            val productListRequestModel = ProductListRequestModel(urc_id = urcid)
//            productListRequestModel.keywords = keyword
//            productListRequestModel.price = filterPrice
//            productListRequestModel.attribute_id = attribute_id
//            when (category) {
//                "features" -> {
//                    productListRequestModel.is_featured = 1
//
//                    fragmentProductListBinding.topnav.tvNavtitle.text = "Featured products"
//                }
//                "essentials" -> {
//                    productListRequestModel.is_essential = 1
//                    fragmentProductListBinding.topnav.tvNavtitle.text = "Daily essential products"
//
//                }
//                "topselling" -> {
//                    productListRequestModel.top_selling = 1
//                    fragmentProductListBinding.topnav.tvNavtitle.text = "Top selling products"
//                }
//                "categoryProduct" -> {
//                    productListRequestModel.category_id = categoryId
//                    fragmentProductListBinding.topnav.tvNavtitle.text = categoryName
//                }
//            }
//
//            when (sortType) {
//                "alphabate" -> {
//                    productListRequestModel.alphabetical = 1
//                }
//                "lowtohigh" -> {
//                    productListRequestModel.is_low_price = 1
//                }
//                "hightolow" -> {
//                    productListRequestModel.is_high_price = 1
//                }
//                "popularity" -> {
//                    productListRequestModel.top_selling = 1
//                }
//            }
//
//            viewModel.productList(
//                productListRequestModel, mCurrentPage.toString()
//            ).observe(mainActivity) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            if (resource.data?.status == true){
//                                resource.data?.let { itProfileInfo ->
//                                    if (isFirstPage) productAdapter?.updateData(
//                                        itProfileInfo.data.productList.data,
//                                        itProfileInfo.productImageUrl
//                                    ) else productAdapter?.addData(
//                                        itProfileInfo.data.productList.data
//                                    )
//                                    mIsLoading = false
//                                    mIsLastPage =
//                                        mCurrentPage == itProfileInfo.data.productList.lastPage
//                                    pageSize = itProfileInfo.data.productList.perPage
//                                    fragmentProductListBinding.tvsubtitle.text =
//                                        " ${itProfileInfo.data.totalProduct} products"
//
//                                    if (itProfileInfo.data.totalProduct > 0) {
//
//                                        fragmentProductListBinding.nodata.root.visibility = View.GONE
//                                    } else {
//
//                                        fragmentProductListBinding.nodata.root.visibility = View.VISIBLE
//                                    }
//                                }
//                            }else{
//                                fragmentProductListBinding.nodata.root.visibility = View.VISIBLE
//                            }
//                            mainActivity.hideProgressDialog()
//
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

    override fun onDestroyView() {
        super.onDestroyView()
        mIsLoading = false
        mIsLastPage = false
        mCurrentPage = 0
        sortType = "popularity"
        keyword = ""
        filterPrice = ""
        filterPrice = ""
        attribute_id = ArrayList<Int>()
    }

    override fun onClick(
        position: Int,
        view: View,
        tvQty: TextView,
        tvPrice: TextView,
        str: String,
        id: String,
        productlist: Data
    ) {

        if (str == "ivImg") {
            val bundle = Bundle()
            bundle.putString("productid", id)
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.nav_productdetails, bundle)
        } else if (str == "tvQty") {
            val builder = AlertDialog.Builder(mainActivity)
            val dialogView = layoutInflater.inflate(R.layout.qty_alert, null)
            builder.setView(dialogView)

            val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rvQtyAlert)
            qtyAlertListAdapter = QtyAlertListAdapter(mainActivity, this@ProductListFragment)
            val quantity_list = ArrayList<QtyAlertListData>()

            quantity_list.add(QtyAlertListData("1 Kg", "₹ 28"))
            quantity_list.add(QtyAlertListData("2 Kg", "₹ 56"))
            quantity_list.add(QtyAlertListData("3 Kg", "₹ 100"))
            quantity_list.add(QtyAlertListData("4 Kg", "₹ 150"))

            recyclerView.layoutManager = LinearLayoutManager(mainActivity)
            recyclerView.adapter = qtyAlertListAdapter
            qtyAlertListAdapter!!.updateQuantityData(quantity_list)

            alert = builder.create()
            alert!!.show()

        } else if (str == "addtocart") {

            productaddtoCart(
                productlist.discount.toString(),
                productlist.id,
                productlist.name,
                productlist.salesPrice.toString(),
                productlist.unitId,
                productlist.unitName
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

        Log.d(TAG, "qty-->" + count)
        Log.d(TAG, "cartid-->" + cartid)

        try {

            when (clicktype) {
                0 -> {


                    if (count<1) {
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
//                productaddtoCart(cartData.urc_product_id.toString(),false)
                }

                1 -> {
                if (count==1) {
//                    Toast.makeText(mainActivity, "Add to cart", Toast.LENGTH_SHORT).show()

                    productaddtoCart(
                        prodcutlist.discount.toString(),
                        prodcutlist.id,
                        prodcutlist.name,
                        prodcutlist.salesPrice.toString(),
                        prodcutlist.unitId,
                        prodcutlist.unitName
                    )
                }else if (count>1){

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

//                productaddtoCart(cartData.urc_product_id.toString(),true)
                }

            }

//            if (count == 0) {
//                productDeleteFromCart(cartid)
//            } else {
//                if (count==1){
//                    productaddtoCart(
//                        prodcutlist.discount.toString(),
//                        prodcutlist.id,
//                        prodcutlist.name,
//                        prodcutlist.salesPrice.toString(),
//                        prodcutlist.unitId,
//                        prodcutlist.unitName
//                    )
//                }else if (count>1)
//                updateCart(
//                    prodcutlist.discount.toString(),
//                    prodcutlist.id,
//                    prodcutlist.name,
//                    count.toString(),
//                    prodcutlist.salesPrice.toString(),
//                    prodcutlist.unitId,
//                    prodcutlist.unitName,
//                    cartid
//                )
//            }
        } catch (e: Exception) {
            Log.d(TAG, "error-->" + e)
        }

    }


//    override fun onClickCategory(
//        position: Int,
//        view: View,
//        recyclerView: RecyclerView,
//        catId: Int,
//        catName: String
//    ) {
//        subCategoryListAdapter = SubCategoryListAdapter(mainActivity, this@ProductListFragment)
//        recyclerView.adapter = subCategoryListAdapter
//        recyclerView.layoutManager = GridLayoutManager(mainActivity, 1)
////        getAllcategory(Shared_Preferences.getUrcid().toString())
//        val subcategoryList = ArrayList<Category>()
//        for (i in 1..5) {
//            subcategoryList.add(Category(2, "", "cleaning"))
//        }
//        subCategoryListAdapter!!.updateData(subcategoryList)
//    }

    override fun onClickSubCategory(position: Int, view: View, catId: Int, catName: String) {
        val bundle = Bundle()
        bundle.putString("viewalltype", "categoryProduct")
        bundle.putString("catName", catName)
        bundle.putInt("catId", catId)
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_productlist, bundle)
    }

    override fun onClick(position: Int, view: View, tvQuantity: TextView, data: QtyAlertListData) {
        alert!!.cancel()
        qtyText!!.text = data.tvQuantity
        priceText!!.text = data.tvRs
    }

    override fun onClick(position: Int, view: View, catId: String, catName: String) {
        val bundle = Bundle()
        bundle.putString("viewalltype", "categoryProduct")
        bundle.putString("catName", catName)
        bundle.putString("catId", catId)
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_productlist, bundle)
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
                                    val builder = android.app.AlertDialog.Builder(mainActivity)
                                    builder.setMessage(resource.data.message)
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
//                                        val navController = Navigation.findNavController(fragmentProductListBinding.root)
//                                        navController.navigate(R.id.nav_cart)
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


    private fun CartList(isFirstPage: Boolean) {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.CartList(
                CartListRequest(
                    customerId = Shared_Preferences.getUserId(),
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

                                        cartArrayList.clear()
                                        cartArrayList.addAll(itResponse.data)
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

                                    if (isFirstPage) productAdapter?.updateData(
                                        productArrayList,
                                        cartArrayList
                                    ) else productAdapter?.addData(
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


    override fun onClickCategory(
        position: Int,
        view: View,
        mProductModelArrayList: com.grocery.sainik_grocery.data.model.categorymodel.Data
    ) {
        val bundle = Bundle()
        bundle.putString("viewalltype", "categoryProduct")
        bundle.putString("catName", mProductModelArrayList.name)
        bundle.putString("catId", mProductModelArrayList.id)
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_productlist, bundle)
    }

}