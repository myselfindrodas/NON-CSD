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
import androidx.recyclerview.widget.GridLayoutManager
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.addresslistmodel.Data
import com.grocery.sainik_grocery.data.model.setprimaryaddressmodel.PrimaryAddressRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentAddressBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.AddressAdapter
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class AddressFragment : Fragment(), AddressAdapter.OnItemClickListener {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentAddressBinding: FragmentAddressBinding
    private var addressAdapter: AddressAdapter? = null

    //val addressModelArrayList: ArrayList<Addres> = arrayListOf()
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAddressBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false)
        val root = fragmentAddressBinding.root
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

        with(fragmentAddressBinding) {
            topnav.tvNavtitle.text = "Address"
            addressAdapter = AddressAdapter(mainActivity, this@AddressFragment)
            rvAddress.adapter = addressAdapter
            rvAddress.layoutManager = GridLayoutManager(mainActivity, 1)
//            val addressList = ArrayList<Addres>()
//            for (i in 0..5){
//                addressList.add(Addres("sd", "asewq", "wdw", "2023-04-06T11:42:31.000000Z", "", "34", 3, 0, "232123", 1, "sdas", "Others", "2023-04-06T11:42:31.000000Z", 2))
//            }
//            addressAdapter!!.updateData(addressList)

            topnav.btnBack.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_home)
//                mainActivity.onBackPressedDispatcher.onBackPressed()
            }

            if (HomeFragment.cartCount > 0) {
                topnav.tvCartCount.text = HomeFragment.cartCount.toString()
                topnav.cvCartCount.visibility = View.VISIBLE
            } else {
                topnav.cvCartCount.visibility = View.GONE
            }
            topnav.ivNotification.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_notification)


            }
            topnav.clCart.setOnClickListener {
                if (Utilities.isClickRecently()) {
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_cart)

            }

            topnav.btnWishlist.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_wishlist)

            }

            btnContinue.setOnClickListener {
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_addaddress)
            }
        }


        getAddressList()
    }


    private fun deleteDialog(mAddressData: Data) {
        val builder = AlertDialog.Builder(mainActivity)
        builder.setTitle("Delete ${mAddressData.type}")
        builder.setMessage("Are you sure you delete ${mAddressData.type} from the list?")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->

            deleteAddress(mAddressData.id)
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
        mAddressModelArrayList: ArrayList<Data>,
        isDelete: Boolean
    ) {

        if (isDelete) {
            deleteDialog(mAddressModelArrayList[position])
        } else {
            mAddressModelArrayList.forEach { item ->
                item.isPrimary = false
            }
            mAddressModelArrayList[position].isPrimary = true
            addressAdapter?.updateData(mAddressModelArrayList)
            primaryAddress(mAddressModelArrayList[position])
        }

    }


    private fun getAddressList() {


        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.addresslist(customerid = Shared_Preferences.getUserId())
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {

                                        addressAdapter?.updateData(itResponse.data)

                                    } else {

//                                        Toast.makeText(
//                                            mainActivity,
//                                            resource.data?.message,
//                                            Toast.LENGTH_SHORT
//                                        ).show()

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

    private fun deleteAddress(id: String) {


        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.deleteaddress(id)
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {
//                                        getAddressList()
                                        val builder = android.app.AlertDialog.Builder(mainActivity)
                                        builder.setMessage(itResponse.message)
                                        builder.setPositiveButton(
                                            "Ok"
                                        ) { dialog, which ->
                                            val navController = Navigation.findNavController(fragmentAddressBinding.root)
                                            navController.navigate(R.id.nav_address)
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
    private fun primaryAddress(data: Data) {


        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.primaryaddress(
                id = data.id, PrimaryAddressRequest(
                    customerId = data.customerId,
                    houseNo = data.houseNo,
                    id = data.id,
                    isPrimary = true,
                    landMark = data.landMark,
                    streetDetails = data.streetDetails,
                    type = data.type
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            resource.data?.let { itResponse ->

                                if (itResponse.status) {
                                    getAddressList()

                                    Toast.makeText(
                                        mainActivity,
                                        itResponse.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    /*val builder = android.app.AlertDialog.Builder(mainActivity)
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
                                    alert.show()*/

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


}