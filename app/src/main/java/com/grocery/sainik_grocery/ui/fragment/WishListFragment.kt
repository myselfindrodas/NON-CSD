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
import com.example.sainikgrocerycustomer.data.model.DataWishListItem
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.deletewishlistmodel.DeletewishlistRequest
import com.grocery.sainik_grocery.data.model.getwishlistmodel.Data
import com.grocery.sainik_grocery.data.model.getwishlistmodel.WishlistRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentWishListBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.WishListAdapter
import com.grocery.sainik_grocery.utils.ItemOffsetDecoration
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class WishListFragment : Fragment(), WishListAdapter.OnItemClickListener {

    lateinit var fragmentWishListBinding: FragmentWishListBinding
    lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel
    private var wishListAdapter:WishListAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentWishListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_wish_list, container, false)
        val root = fragmentWishListBinding.root
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

        wishListAdapter = WishListAdapter(mainActivity, this@WishListFragment)
        fragmentWishListBinding.rvWishlist.adapter = wishListAdapter
        fragmentWishListBinding.rvWishlist.layoutManager =
            GridLayoutManager(mainActivity, 2)
//        fragmentWishListBinding.rvWishlist.addOnScrollListener(recyclerOnScroll)
        //productAdapter?.updateData(categoryModelArrayList)
        val itemDecoration = ItemOffsetDecoration(mainActivity, R.dimen.photos_list_spacing1)
        fragmentWishListBinding.rvWishlist.addItemDecoration(itemDecoration)
        fragmentWishListBinding.topnav.tvNavtitle.text = "Wish List"
//
//        val wishList = ArrayList<DataWishListItem>()
//        for (i in 1..5){
//            wishList.add(DataWishListItem("2023-04-12T10:07:18.000000Z", "", 15, 1, "2023-04-12T10:07:18.000000Z", 2, 12.5, 6, 2, "shop", "", "Kg", 77, 88))
//        }
//        wishListAdapter!!.updateData(wishList, "R.drawable.item")



        fragmentWishListBinding.topnav.btnBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        if (HomeFragment.cartCount>0) {
            fragmentWishListBinding.topnav.tvCartCount.text = HomeFragment.cartCount.toString()
            fragmentWishListBinding.topnav.cvCartCount.visibility=View.VISIBLE
        }else{
            fragmentWishListBinding.topnav.cvCartCount.visibility=View.GONE
        }


        fragmentWishListBinding.topnav.clCart.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)


        }

        fragmentWishListBinding.topnav.ivNotification.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)


        }

        fragmentWishListBinding.nodata.btnDashboard.setOnClickListener {

            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.nav_home)
        }

        wishList()
    }



    private fun wishList(){

        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.wishlist(WishlistRequest(customerId = Shared_Preferences.getUserId()))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {
                                        if (itResponse.data.isNullOrEmpty()) {
                                            viewModel.cartListItem.value = 0

                                            wishListAdapter?.updateData(itResponse.data)
                                            fragmentWishListBinding.nodata.root.visibility=View.VISIBLE
                                        }else{
                                            fragmentWishListBinding.nodata.root.visibility=View.GONE
                                            wishListAdapter?.updateData(itResponse.data)

                                        }
                                         fragmentWishListBinding.tvsubtitle.text = "Total ${itResponse.totalCount} products"
                                    } else {
                                        fragmentWishListBinding.tvsubtitle.text = ""

                                        wishListAdapter?.updateData(arrayListOf())
                                        fragmentWishListBinding.nodata.root.visibility=View.VISIBLE
                                       /* Toast.makeText(
                                            mainActivity,
                                            resource.data.message,
                                            Toast.LENGTH_SHORT
                                        ).show()*/

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




    private fun deleteDialog(mWishlistdata: Data){
        val builder = AlertDialog.Builder(mainActivity)
        builder.setMessage("Are you sure you delete this item from the list?")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->

            deleteWishList(mWishlistdata.id.toString())
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which ->

            dialog.dismiss()
        }
        val alert = builder.create()
        alert.setOnShowListener { arg0 ->
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.orange))
            alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.orange))
        }
        alert.show()
    }



    override fun onClick(
        position: Int,
        view: View,
        mWishListModelArrayList: ArrayList<Data>,
        isDelete: Boolean
    ) {
        if (isDelete){
            deleteDialog(mWishListModelArrayList[position])
        }else{

            val bundle = Bundle()
            bundle.putString("productid", mWishListModelArrayList[position].productId)
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.nav_productdetails, bundle)
        }
    }


//
    private fun deleteWishList(id:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.deletewishlist(DeletewishlistRequest(id = id))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {
                                        wishList()
                                        val builder = android.app.AlertDialog.Builder(mainActivity)
                                        builder.setMessage(itResponse.message)
                                        builder.setPositiveButton(
                                            "Ok"
                                        ) { dialog, which ->

                                            dialog.dismiss()
                                        }
                                        val alert = builder.create()
                                        alert.setOnShowListener { arg0 ->
                                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                                .setTextColor(resources.getColor(R.color.orange))
                                        }
                                      //  alert.show()

                                    } else {

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




}