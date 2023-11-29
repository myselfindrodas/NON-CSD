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
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sainikgrocerycustomer.data.model.Order
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.orderlistmodel.Data
import com.grocery.sainik_grocery.data.model.orderlistmodel.OrderlistRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentMyorderBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.MyOrderListAdapter
import com.grocery.sainik_grocery.utils.ItemOffsetDecoration
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel


class MyOrderFragment : Fragment(),MyOrderListAdapter.OnItemClickListener {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentMyOrderBinding: FragmentMyorderBinding
    private var productAdapter: MyOrderListAdapter? = null
    private lateinit var viewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMyOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_myorder, container, false)
        val root = fragmentMyOrderBinding.root
        mainActivity = activity as MainActivity

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

        fragmentMyOrderBinding.topnav.tvNavtitle.text = "My orders"

        with(fragmentMyOrderBinding){
            productAdapter = MyOrderListAdapter(mainActivity, this@MyOrderFragment)
            rvProductList.adapter = productAdapter
            rvProductList.layoutManager = GridLayoutManager(mainActivity, 1)
            val itemDecoration = ItemOffsetDecoration(mainActivity, R.dimen.photos_list_spacing)
           // rvProductList.addItemDecoration(itemDecoration)
//            val orderList = ArrayList<Order>()
//            for (i in 1..5){
//                orderList.add(Order(3,132.4, "2023-04-10T12:12:21.000000Z", "", 0.2, 1, "SAINIK532984", 0, 1, 0, "2023-04-10T12:12:21.000000Z", 2, "asewq", "sdas", "wdw"))
//            }
//            productAdapter!!.updateData(orderList, "R.drawablw.item")

            topnav.clCart.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_cart)
            }

            if (HomeFragment.cartCount>0) {
                topnav.tvCartCount.text = HomeFragment.cartCount.toString()
                topnav.cvCartCount.visibility=View.VISIBLE
            }else{
                topnav.cvCartCount.visibility=View.GONE
            }
            topnav.ivNotification.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_notification)


            }

            topnav.btnWishlist.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_wishlist)

            }

            topnav.btnBack.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                mainActivity.onBackPressedDispatcher.onBackPressed()
            }

//        viewModel.cartListItem.observe(viewLifecycleOwner, Observer {count->
//            if (count>0) {
//                topnav.tvCartCount.text = count.toString()
//                topnav.cvCartCount.visibility=View.VISIBLE
//            }else{
//                topnav.cvCartCount.visibility=View.GONE
//            }
//
//            HomeFragment.cartCount=count
//        })
        }
        getMyOrderList()
//        productCartList()
    }

//    private fun productCartList() {
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            viewModel.cartList()
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                resource.data.let {itResponse->
//
//                                    if (itResponse?.status == true) {
//
//                                        //cartCount=itResponse.data.cart.size
//                                        if (itResponse.data.cart.isNullOrEmpty()) {
//                                            viewModel.cartListItem.value = 0
//                                        }else
//                                            viewModel.cartListItem.value= itResponse.data.cart.size
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
    private fun getMyOrderList() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.orderlist(OrderlistRequest(customerId = Shared_Preferences.getUserId(),
                pageSize = 10, skip = 0))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                 mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {
                                        productAdapter?.updateData(itResponse.data)

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




    override fun onClick(position: Int, view: View, mOrderData: Data) {


        val bundle = Bundle()
        bundle.putString("orderId", mOrderData.id.toString())

        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_order_details,bundle)
    }

}