package com.grocery.sainik_grocery.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.sainikgrocerycustomer.data.model.Category
import com.example.sainikgrocerycustomer.data.model.DataProductList
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartListRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentHomeBinding
import com.grocery.sainik_grocery.ui.LocationActivity
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.CategoryAdapter
import com.grocery.sainik_grocery.ui.adapter.SlidingBannerAdapter
import com.grocery.sainik_grocery.ui.adapter.TopsellingAdapter
import com.grocery.sainik_grocery.utils.ItemOffsetDecoration
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import java.util.Locale
import java.util.Objects


class HomeFragment : Fragment(),
    TopsellingAdapter.OnItemClickListener, CategoryAdapter.OnItemClickListener,
    SlidingBannerAdapter.OnItemClickListener{
    lateinit var mainActivity: MainActivity
    lateinit var fragmentHomeBinding: FragmentHomeBinding
    private var groupAdapter: CategoryAdapter? = null
    private var categoryAdapter: CategoryAdapter? = null
    private var topsellingAdapter: TopsellingAdapter? = null
    private var featuredproductAdapter: TopsellingAdapter? = null
    private var dailyproductAdapter: TopsellingAdapter? = null
    private var slidingBannerAdapter: SlidingBannerAdapter? = null
    private var slidingBannerAdapter1: SlidingBannerAdapter? = null
    var btnNavlogout: LinearLayout? = null
    var btnExpandaccount: LinearLayout? = null
    private lateinit var viewModel: CommonViewModel
    private val REQUEST_CODE_SPEECH_INPUT = 1
    private lateinit var bannerImageArray: ArrayList<Int>
    private lateinit var bannerImageArray2: ArrayList<Int>
    private var slidingDotsCount = 0
    private lateinit var slidingImageDots: Array<ImageView?>
    private var slidingDotsCount1 = 0
    private lateinit var slidingImageDots1: Array<ImageView?>
    private val headerHandler = Handler()
    var currentPage = 0
    var delay = 2000
    var runnable: Runnable? = null

    companion object {
        var cartCount = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mainActivity.finishAffinity()
                    if (activity != null) {
                        activity?.finish()
                    }

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val root = fragmentHomeBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bannerImageArray = arrayListOf()
        bannerImageArray.add(R.drawable.bgbanner4)
        bannerImageArray.add(R.drawable.banner1)
        bannerImageArray.add(R.drawable.banner2)
        bannerImageArray.add(R.drawable.banner3)

        slidingBannerAdapter= SlidingBannerAdapter(mainActivity, this@HomeFragment)

        slidingBannerAdapter!!.updateBannerImage(bannerImageArray)
        fragmentHomeBinding.slidingBanner.apply {
            adapter = slidingBannerAdapter
            registerOnPageChangeCallback(slidingCallback)

        }

        slidingDotsCount = bannerImageArray.size

        slidingImageDots = arrayOfNulls(slidingDotsCount)
        fragmentHomeBinding.sliderDots.removeAllViews()
        for (i in 0 until slidingDotsCount) {
            slidingImageDots[i] = ImageView(mainActivity)
            slidingImageDots[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    mainActivity,
                    R.drawable.non_active_dot
                )
            )
            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            params.setMargins(8, 0, 8, 0)
            fragmentHomeBinding.sliderDots.addView(slidingImageDots[i], params)
        }


        slidingImageDots[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                mainActivity,
                R.drawable.active_dot
            )
        )

        bannerImageArray2 = arrayListOf()
        bannerImageArray2.add(R.drawable.bgbanner4)
        bannerImageArray2.add(R.drawable.bgbanner1)
        bannerImageArray2.add(R.drawable.bgbanner2)
        bannerImageArray2.add(R.drawable.bgbanner3)

        slidingBannerAdapter1= SlidingBannerAdapter(mainActivity, this@HomeFragment)

        slidingBannerAdapter1!!.updateBannerImage(bannerImageArray)
        fragmentHomeBinding.slidingBannerViewPager.apply {
            adapter = slidingBannerAdapter1
            registerOnPageChangeCallback(slidingCallback1)

        }

        slidingDotsCount1 = bannerImageArray2.size

        slidingImageDots1 = arrayOfNulls(slidingDotsCount1)
        fragmentHomeBinding.sliderDotsOfBanner.removeAllViews()
        for (i in 0 until slidingDotsCount1) {
            slidingImageDots1[i] = ImageView(mainActivity)
            slidingImageDots1[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    mainActivity,
                    R.drawable.non_active_dot
                )
            )
            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            params.setMargins(8, 0, 8, 0)
            fragmentHomeBinding.sliderDotsOfBanner.addView(slidingImageDots1[i], params)
        }


        slidingImageDots1[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                mainActivity,
                R.drawable.active_dot
            )
        )


        topsellingAdapter = TopsellingAdapter(mainActivity, this@HomeFragment)
        fragmentHomeBinding.rvTopsellingproduct.setAdapter(topsellingAdapter)
        fragmentHomeBinding.rvTopsellingproduct.setLayoutManager(
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        )
        val topSellingList = ArrayList<DataProductList>()
        for (i in 1..5){
            topSellingList.add(DataProductList(70.5, 60.5, 1, R.drawable.item, "Salt", 25.5, "Kg", 1))
        }
        topsellingAdapter!!.updateData(topSellingList, "R.drawable.item")

        featuredproductAdapter = TopsellingAdapter(mainActivity, this@HomeFragment)
        fragmentHomeBinding.rvFeaturesellingproduct.setAdapter(featuredproductAdapter)
        fragmentHomeBinding.rvFeaturesellingproduct.setLayoutManager(
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false))
        val itemDecoration2 = ItemOffsetDecoration(mainActivity, R.dimen.product_list_spacing)
        fragmentHomeBinding.rvFeaturesellingproduct.addItemDecoration(itemDecoration2)
        val featuredproductList = ArrayList<DataProductList>()
        for (i in 1..5){
            featuredproductList.add(DataProductList(70.5, 60.5, 1, R.drawable.item, "Salt", 25.5, "Kg", 1))
        }
        featuredproductAdapter!!.updateData(featuredproductList, "R.drawable.item")

        dailyproductAdapter = TopsellingAdapter(mainActivity, this@HomeFragment)
        fragmentHomeBinding.rvDailyproduct.setAdapter(dailyproductAdapter)
        fragmentHomeBinding.rvDailyproduct.setLayoutManager(
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        )
        val dailyproductList = ArrayList<DataProductList>()
        for (i in 1..5){
            dailyproductList.add(DataProductList(70.5, 60.5, 1, R.drawable.item, "Salt", 25.5, "Kg", 1))
        }
        dailyproductAdapter!!.updateData(dailyproductList, "R.drawable.item")

        groupAdapter = CategoryAdapter(mainActivity, this@HomeFragment)
        fragmentHomeBinding.rvGroup.setAdapter(groupAdapter)
        fragmentHomeBinding.rvGroup.setLayoutManager(GridLayoutManager(mainActivity, 3))
        val itemDecoration1 = ItemOffsetDecoration(mainActivity, R.dimen.photos_list_spacing)
        fragmentHomeBinding.rvGroup.addItemDecoration(itemDecoration1)
//        val groupList = ArrayList<Category>()
//        for (i in 1..5){
//            groupList.add(Category(2, R.drawable.item, "Cleaning"))
//        }
//        groupAdapter!!.updateData(groupList, "R.drawable.item")

        categoryAdapter = CategoryAdapter(mainActivity, this@HomeFragment)
        fragmentHomeBinding.idGVcourses.setAdapter(categoryAdapter)
        fragmentHomeBinding.idGVcourses.setLayoutManager(GridLayoutManager(mainActivity, 3))
        val itemDecoration = ItemOffsetDecoration(mainActivity, R.dimen.photos_list_spacing)
        fragmentHomeBinding.idGVcourses.addItemDecoration(itemDecoration)
//        val categoryList = ArrayList<Category>()
//        for (i in 1..3){
//            categoryList.add(Category(2, R.drawable.item, "Cleaning"))
//        }
//        categoryAdapter!!.updateData(categoryList, "R.drawable.item")

//        viewModel.cartListItem.observe(viewLifecycleOwner, Observer { count ->
//            if (count > 0) {
//                fragmentHomeBinding.topnav.tvCartCount.text = count.toString()
//                fragmentHomeBinding.topnav.cvCartCount.visibility = View.VISIBLE
//            } else {
//                fragmentHomeBinding.topnav.cvCartCount.visibility = View.GONE
//            }
//            cartCount = count
//        })

//        dashboard(Shared_Preferences.getUrcid().toString())

//        productCartList()

      /*
      fragmentHomeBinding.topnav.btnWishlist.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wishlist)

       }

      fragmentHomeBinding.topnav.clCart.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)
        }
*/

        fragmentHomeBinding.topnav.ivProfile.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_profile)

        }


        fragmentHomeBinding.topnav.tvMyAddress.text = LocationActivity.locationAddress

        fragmentHomeBinding.topnav.iv.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            mainActivity.openDrawer()
        }



        fragmentHomeBinding.topnav.tvUrcName.text = MainActivity.URCName.replaceFirstChar { char ->
            char.uppercaseChar()
        }

        fragmentHomeBinding.topsellingViewall.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("viewalltype", "topselling")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_productlist, bundle)
        }

        fragmentHomeBinding.featureproductViewall.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("viewalltype", "features")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_productlist, bundle)
        }

        fragmentHomeBinding.dailyproductViewall.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("viewalltype", "essentials")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_productlist, bundle)
        }


        fragmentHomeBinding.btnPartyorder.setOnClickListener {


            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_partycategory)

        }


//        fragmentHomeBinding.shopbycategoryViewall.setOnClickListener {
//
//            val bundle = Bundle()
//            bundle.putString("viewalltype", "category")
//            val navController = Navigation.findNavController(it)
//            navController.navigate(R.id.nav_productlist, bundle)
//
//        }

        fragmentHomeBinding.topnav.ivMic.setOnClickListener {

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

        fragmentHomeBinding.topnav.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s?.length!! > 1) {
//                    dashboard(Shared_Preferences.getUrcid().toString(), s.toString())
                } else {
//                    dashboard(Shared_Preferences.getUrcid().toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })


        categorylist()
    }

    private val slidingCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            for (i in 0 until slidingDotsCount) {
                slidingImageDots[i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        mainActivity,
                        R.drawable.non_active_dot
                    )
                )
            }

            slidingImageDots[position]?.setImageDrawable(
                ContextCompat.getDrawable(
                    mainActivity,
                    R.drawable.active_dot
                )
            )
        }
    }

    private val slidingCallback1 = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            for (i in 0 until slidingDotsCount) {
                slidingImageDots1[i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        mainActivity,
                        R.drawable.non_active_dot
                    )
                )
            }

            slidingImageDots1[position]?.setImageDrawable(
                ContextCompat.getDrawable(
                    mainActivity,
                    R.drawable.active_dot
                )
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {

                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                fragmentHomeBinding.topnav.etSearch.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }


    private fun categorylist(){
        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.categorylist()
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data.let { itResponse ->

                                    if (itResponse?.status == true) {

                                        categoryAdapter?.updateData(itResponse.data)
                                        groupAdapter?.updateData(itResponse.data)

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


    private fun productCartList() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.CartList(CartListRequest(customerId = Shared_Preferences.getUserId(), pageSize = 10, skip = 0))
                .observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data.let {itResponse->

                                    if (itResponse?.status == true) {


                                        if (itResponse.totalCount==0){
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible = false
                                        }else{
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).isVisible = true
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).number = itResponse.totalCount
                                            mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_basket).backgroundColor = Color.parseColor("#E63425")

                                        }






                                    } else {
//                                        viewModel.cartListItem.value=0
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
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }

    }





//    private fun dashboard(urcid: String, keyword: String = "") {
//
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            viewModel.dashboard(
//                DashboardRequestModel(urc_id = urcid, keywords = keyword)
//            ).observe(mainActivity) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            resource.data?.let { itProfileInfo ->
//
//                                with(fragmentHomeBinding) {
//
//                                    if (itProfileInfo.data.topSellingProducts.isNullOrEmpty()) {
//                                        llTopSellingName.visibility = View.GONE
//                                    } else {
//                                        llTopSellingName.visibility = View.VISIBLE
//                                    }
//                                    topsellingAdapter?.updateData(
//                                        itProfileInfo.data.topSellingProducts as ArrayList<DataProductList>,
//                                        itProfileInfo.productImageUrl
//                                    )
//
//                                    if (itProfileInfo.data.featuredProducts.isNullOrEmpty()) {
//                                        llFeatureProductName.visibility = View.GONE
//                                    } else {
//                                        llFeatureProductName.visibility = View.VISIBLE
//                                    }
//                                    featuredproductAdapter?.updateData(
//                                        itProfileInfo.data.featuredProducts as ArrayList<DataProductList>,
//                                        itProfileInfo.productImageUrl
//                                    )
//
//                                    if (itProfileInfo.data.essentialProducts.isNullOrEmpty()) {
//                                        llEssentialName.visibility = View.GONE
//                                    } else {
//                                        llEssentialName.visibility = View.VISIBLE
//                                    }
//                                    dailyproductAdapter?.updateData(
//                                        itProfileInfo.data.essentialProducts as ArrayList<DataProductList>,
//                                        itProfileInfo.productImageUrl
//                                    )
//
//                                    if (itProfileInfo.data.category.isNullOrEmpty()) {
//                                        llCategoryName.visibility = View.GONE
//                                    } else {
//                                        llCategoryName.visibility = View.VISIBLE
//                                    }
//                                    categoryAdapter?.updateData(
//                                        itProfileInfo.data.category as ArrayList<Category>,
//                                        itProfileInfo.categoryImageUrl
//                                    )
//
//                                    groupAdapter?.updateData(
//                                        itProfileInfo.data.category as ArrayList<Category>,
//                                        itProfileInfo.categoryImageUrl
//                                    )
//
//
//                                }
//
//                            }
//
//                            mainActivity.hideProgressDialog()
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
//                                resource.data.let { itResponse ->
//
//                                    if (itResponse?.status == true) {
//
//                                        //cartCount=itResponse.data.cart.size
//                                        if (itResponse.data.cart.isNullOrEmpty()) {
//                                            viewModel.cartListItem.value = 0
//                                        } else
//                                            viewModel.cartListItem.value = itResponse.data.cart.size
//
//                                    } else {
//
//                                        viewModel.cartListItem.value = 0
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
//                                mainActivity.hideProgressDialog()
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

    override fun onClickCategory(position: Int, view: View, catId: String, catName: String) {

        val bundle = Bundle()
        bundle.putString("viewalltype", "categoryProduct")
        bundle.putString("catName", catName)
        bundle.putString("catId", catId)
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_productlist, bundle)
    }



    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(true)

        headerHandler.postDelayed(Runnable {
            headerHandler.postDelayed(runnable!!, delay.toLong())
            if (currentPage === slidingBannerAdapter!!.itemCount + 1 - 1) {
                currentPage = 0
            }
//
//            if (currentPage === slidingBannerAdapter1!!.itemCount + 1 - 1) {
//                currentPage = 0
//            }
            fragmentHomeBinding.slidingBanner.setCurrentItem(currentPage++, true)
//            fragmentHomeBinding.slidingBannerViewPager.setCurrentItem(currentPage++, true)

        }.also { runnable = it }, delay.toLong())
        super.onResume()

        productCartList()
    }


    override fun onPause() {
        super.onPause()
        headerHandler.removeCallbacks(runnable!!)
    }


    override fun onStop() {
        super.onStop()
        headerHandler.removeCallbacks(runnable!!)
    }





    override fun onClick(position: Int, view: View) {

    }



    override fun onClick(position: Int, view: View, id: Int) {
        val bundle = Bundle()
        bundle.putString("productid", id.toString())
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_productdetails, bundle)
    }
}