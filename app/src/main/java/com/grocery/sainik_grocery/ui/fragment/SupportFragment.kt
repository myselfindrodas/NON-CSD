package com.grocery.sainik_grocery.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sainikgrocerycustomer.data.model.AppContent
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentSupportBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.FAQ_Help_Adapter
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import kotlin.collections.ArrayList

class SupportFragment : Fragment(), FAQ_Help_Adapter.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    lateinit var fragmentSupportBinding: FragmentSupportBinding
    private lateinit var viewModel: CommonViewModel
    private var detailsAdapter: FAQ_Help_Adapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSupportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_support, container, false)
        val root = fragmentSupportBinding.root
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


        fragmentSupportBinding.topnav.tvNavtitle.text = "Helps and Support"

        detailsAdapter =
            FAQ_Help_Adapter(
                mainActivity,
                this@SupportFragment
            )
        fragmentSupportBinding.rvProductList.adapter = detailsAdapter
        fragmentSupportBinding.rvProductList.layoutManager = GridLayoutManager(mainActivity, 1)
        val faqList = ArrayList<AppContent>()
        for (i in 1..5){
            faqList.add(AppContent("", "", "Receive notification relat to order status, payment and support Aut haec tibi, Torquate, sunt vituperanda aut patrocinium voluptatis repudian dum. Quod si ita se habeat, non possit beatam praestare vitam sapientia.", 8, 1, "How to Check status of My Order", 1, ""))
        }
        detailsAdapter!!.updateData(faqList)
/*

        fragmentSupportBinding.btnMyoderexpand.setOnClickListener {

            fragmentSupportBinding.btnMyoderexpand.visibility = View.GONE
            fragmentSupportBinding.btnMyodercollapse.visibility = View.VISIBLE
            fragmentSupportBinding.tvMyorderdetails.visibility = View.VISIBLE
        }
//477608894

        fragmentSupportBinding.btnMyodercollapse.setOnClickListener {

            fragmentSupportBinding.btnMyoderexpand.visibility = View.VISIBLE
            fragmentSupportBinding.btnMyodercollapse.visibility = View.GONE
            fragmentSupportBinding.tvMyorderdetails.visibility = View.GONE
        }
*/


        fragmentSupportBinding.topnav.ivNotification.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)


        }

        fragmentSupportBinding.topnav.btnWishlist.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wishlist)

        }

        if (HomeFragment.cartCount>0) {
            fragmentSupportBinding.topnav.tvCartCount.text = HomeFragment.cartCount.toString()
            fragmentSupportBinding.topnav.cvCartCount.visibility=View.VISIBLE
        }else{
            fragmentSupportBinding.topnav.cvCartCount.visibility=View.GONE
        }
        fragmentSupportBinding.topnav.clCart.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)

        }
        fragmentSupportBinding.topnav.btnBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

//        getDetails()
    }
//    private fun getDetails() {
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            viewModel.getSupportAndFAQ("1")
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                resource.data?.let { itResponse ->
//
//                                    if (itResponse.status) {
//
//                                        detailsAdapter?.updateData(itResponse.data.appContent)
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
//
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
        id: Int,
        s: String,
        mNotificationListModelArrayList: ArrayList<AppContent>
    ) {


    }
}