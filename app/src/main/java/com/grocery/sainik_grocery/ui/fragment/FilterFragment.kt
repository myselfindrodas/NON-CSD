package com.grocery.sainik_grocery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sainikgrocerycustomer.data.model.Attribute
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentFilterBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.FilterPackAdapter
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel


class FilterFragment : Fragment(), FilterPackAdapter.OnPackItemClickListener {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentFilterBinding: FragmentFilterBinding
    private lateinit var viewModel: CommonViewModel
    private var filterPackAdapter: FilterPackAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // filterResponse
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFilterBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false)
        val root = fragmentFilterBinding.root
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentFilterBinding.topnav.tvNavtitle.text = "Filter by"

        resetLayout()
        with(fragmentFilterBinding) {
            llPrice.setOnClickListener {

                resetLayout()

                llSubPrice.visibility = View.VISIBLE
                ivPriceArrow.rotation = 270f
            }

            ll50.setOnClickListener {
                resetPriceCheckBok()
                cb50.isChecked = true
            }
            ll100.setOnClickListener {
                resetPriceCheckBok()
                cb100.isChecked = true
            }
            ll200.setOnClickListener {
                resetPriceCheckBok()
                cb200.isChecked = true
            }
            ll300.setOnClickListener {
                resetPriceCheckBok()
                cb300.isChecked = true
            }
            ll400.setOnClickListener {
                resetPriceCheckBok()
                cb400.isChecked = true
            }
            ll500.setOnClickListener {
                resetPriceCheckBok()
                cb500.isChecked = true
            }
            ll600.setOnClickListener {
                resetPriceCheckBok()
                cb600.isChecked = true
            }
            ll700.setOnClickListener {
                resetPriceCheckBok()
                cb700.isChecked = true
            }
            ll800.setOnClickListener {
                resetPriceCheckBok()
                cb800.isChecked = true
            }
            /*llBrand.setOnClickListener {
                resetLayout()

               llSubBrand.visibility=View.VISIBLE
                ivBrandArrow.rotation=270f
            }

            llDiscount.setOnClickListener {
                resetLayout()

               llSubDiscount.visibility=View.VISIBLE
                ivDiscountArrow.rotation=270f
            }*/

            btnApply.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = findNavController()
                when {
                    cb50.isChecked -> {

                        ProductListFragment.filterPrice = "50"
                    }
                    cb100.isChecked -> {

                        ProductListFragment.filterPrice = "100"
                    }
                    cb200.isChecked -> {

                        ProductListFragment.filterPrice = "200"
                    }
                    cb300.isChecked -> {

                        ProductListFragment.filterPrice = "300"
                    }
                    cb400.isChecked -> {

                        ProductListFragment.filterPrice = "400"
                    }
                    cb500.isChecked -> {

                        ProductListFragment.filterPrice = "500"
                    }
                    cb600.isChecked -> {

                        ProductListFragment.filterPrice = "600"
                    }
                    cb700.isChecked -> {

                        ProductListFragment.filterPrice = "700"
                    }
                    cb800.isChecked -> {

                        ProductListFragment.filterPrice = "800"
                    }
                }

                filterPackAdapter?.getList()!!.let { listItem ->
                    ProductListFragment.attribute_id.clear()
                    listItem.forEach { atribute ->
                        if (atribute.isChecked)
                            ProductListFragment.attribute_id.add(atribute.id)
                    }
                }
                navController.popBackStack()
            }

            btnCancel.setOnClickListener {

                mainActivity.onBackPressedDispatcher.onBackPressed()
            }
            llPack.setOnClickListener {
                resetLayout()

                rvPackList.visibility = View.VISIBLE
                ivPackSizeArrow.rotation = 270f
            }
            topnav.btnBack.setOnClickListener {

                mainActivity.onBackPressedDispatcher.onBackPressed()
            }


            topnav.ivNotification.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_notification)


            }

            topnav.btnWishlist.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_wishlist)

            }

            if (HomeFragment.cartCount > 0) {
                topnav.tvCartCount.text = HomeFragment.cartCount.toString()
                topnav.cvCartCount.visibility = View.VISIBLE
            } else {
                topnav.cvCartCount.visibility = View.GONE
            }

            filterPackAdapter = FilterPackAdapter(mainActivity, this@FilterFragment)
            rvPackList.adapter = filterPackAdapter
            rvPackList.layoutManager = GridLayoutManager(mainActivity, 1)

            val filterList = ArrayList<Attribute>()
            for (i in 1..5){
                filterList.add(Attribute("","", 1, "1Kg", 1, null))
            }
            filterPackAdapter!!.updateData(filterList)

            topnav.clCart.setOnClickListener {
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_cart)

            }
//            getFilterAttributeList()
        }
    }

    private fun resetLayout() {
        with(fragmentFilterBinding) {
            ivPriceArrow.rotation = 90f
            ivPackSizeArrow.rotation = 90f
            /*llSubBrand.visibility=View.GONE
            ivBrandArrow.rotation=90f
            ivDiscountArrow.rotation=90f
            llSubDiscount.visibility=View.GONE
            llSubPackSize.visibility=View.GONE*/
            llSubPrice.visibility = View.GONE
            rvPackList.visibility = View.GONE
        }
    }

    private fun resetPriceCheckBok() {
        with(fragmentFilterBinding) {
            cb50.isChecked = false
            cb100.isChecked = false
            cb200.isChecked = false
            cb300.isChecked = false
            cb400.isChecked = false
            cb500.isChecked = false
            cb600.isChecked = false
            cb700.isChecked = false
            cb800.isChecked = false
        }
    }

//    private fun getFilterAttributeList() {
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            viewModel.filterResponse()
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                resource.data?.let { itResponse ->
//
//                                    if (itResponse.status) {
//                                        filterPackAdapter?.updateData(itResponse.data.attribute)
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

    override fun onPackClick(position: Int, view: View) {


    }

}