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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sainikgrocerycustomer.data.model.DataNotification
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.notificationmodel.Data
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentNotificationBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.NotificationAdapter
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class NotificationFragment : Fragment(), NotificationAdapter.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    lateinit var fragmentNotificationBinding: FragmentNotificationBinding
    private var notificationAdapter: NotificationAdapter?= null
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentNotificationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        val root = fragmentNotificationBinding.root
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


        fragmentNotificationBinding.topnav.tvNavtitle.text = "Notifications"


        notificationAdapter = NotificationAdapter(mainActivity, this@NotificationFragment)
        fragmentNotificationBinding.rvNotification.setAdapter(notificationAdapter)
        fragmentNotificationBinding.rvNotification.setLayoutManager(
            LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false))
//        val notificationList = ArrayList<DataNotification>()
//        for (i in 1..5){
//            notificationList.add(DataNotification("2023-04-10T12:12:21.000000Z","", "test", 1, 1, "test", 1, 1, "", 2))
//        }
//        notificationAdapter!!.updateData(notificationList)

        if (HomeFragment.cartCount>0) {
            fragmentNotificationBinding.topnav.tvCartCount.text = HomeFragment.cartCount.toString()
            fragmentNotificationBinding.topnav.cvCartCount.visibility=View.VISIBLE
        }else{
            fragmentNotificationBinding.topnav.cvCartCount.visibility=View.GONE
        }


        fragmentNotificationBinding.topnav.clCart.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)


        }
        fragmentNotificationBinding.topnav.btnBack.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_home)

        }

        fragmentNotificationBinding.topnav.clCart.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)


        }

        fragmentNotificationBinding.nodata.btnDashboard.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.nav_home)
        }

        fragmentNotificationBinding.topnav.btnWishlist.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wishlist)

        }

        notificationList()

    }


    private fun notificationList(){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.notificationlist(customerid = Shared_Preferences.getUserId())
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {
                                        notificationAdapter?.updateData(itResponse.data)
                                        if (itResponse.data.isNullOrEmpty()){
                                            fragmentNotificationBinding.nodata.root.visibility=View.VISIBLE
                                        }else{
                                            fragmentNotificationBinding.nodata.root.visibility=View.GONE

                                        }

                                    } else {

                                        fragmentNotificationBinding.nodata.root.visibility=View.VISIBLE
                                        Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

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







    override fun onClick(
        position: Int,
        view: View,
        mNotificationListModelArrayList: ArrayList<Data>
    ) {


    }


}