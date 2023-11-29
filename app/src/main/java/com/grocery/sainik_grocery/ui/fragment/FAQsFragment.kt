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
import com.grocery.sainik_grocery.databinding.FragmentFAQsBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.FAQ_Help_Adapter
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel


class FAQsFragment : Fragment(), FAQ_Help_Adapter.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    lateinit var fragmentFAQsBinding: FragmentFAQsBinding
    private var detailsAdapter: FAQ_Help_Adapter? = null
    private lateinit var viewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFAQsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_f_a_qs, container, false)
        val root = fragmentFAQsBinding.root
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

        fragmentFAQsBinding.topnav.tvNavtitle.text = "FAQs"

        detailsAdapter =
            FAQ_Help_Adapter(
                mainActivity,
                this@FAQsFragment,
                true
            )
        fragmentFAQsBinding.rvProductList.adapter = detailsAdapter
        fragmentFAQsBinding.rvProductList.layoutManager = GridLayoutManager(mainActivity, 1)
        val faqList = ArrayList<AppContent>()
        for (i in 1..5){
            faqList.add(AppContent("", "", "Receive notification relat to order status, payment and support Aut haec tibi, Torquate, sunt vituperanda aut patrocinium voluptatis repudian dum. Quod si ita se habeat, non possit beatam praestare vitam sapientia.", 8, 1, "How to Check status of My Order", 1, ""))
        }
        detailsAdapter!!.updateData(faqList)

        fragmentFAQsBinding.topnav.clCart.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)
        }
        fragmentFAQsBinding.topnav.btnBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }
        if (HomeFragment.cartCount>0) {
            fragmentFAQsBinding.topnav.tvCartCount.text = HomeFragment.cartCount.toString()
            fragmentFAQsBinding.topnav.cvCartCount.visibility=View.VISIBLE
        }else{
            fragmentFAQsBinding.topnav.cvCartCount.visibility=View.GONE
        }
        fragmentFAQsBinding.topnav.ivNotification.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)

        }

        fragmentFAQsBinding.topnav.btnWishlist.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wishlist)

        }

//        getDetails()
    }



//    private fun getDetails() {
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            viewModel.getSupportAndFAQ("2")
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