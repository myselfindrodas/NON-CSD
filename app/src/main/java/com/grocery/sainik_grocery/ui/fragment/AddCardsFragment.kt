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
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentAddCardsBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class AddCardsFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentAddCardsBinding: FragmentAddCardsBinding
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAddCardsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_cards, container, false)
        val root = fragmentAddCardsBinding.root
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

        fragmentAddCardsBinding.topnav.tvNavtitle.text = "Add card"


        fragmentAddCardsBinding.topnav.clCart.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)


        }

        fragmentAddCardsBinding.topnav.ivNotification.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)


        }


        fragmentAddCardsBinding.topnav.btnWishlist.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wishlist)


        }

        if (HomeFragment.cartCount>0) {
            fragmentAddCardsBinding.topnav.tvCartCount.text = HomeFragment.cartCount.toString()
            fragmentAddCardsBinding.topnav.cvCartCount.visibility=View.VISIBLE
        }else{
            fragmentAddCardsBinding.topnav.cvCartCount.visibility=View.GONE
        }

        fragmentAddCardsBinding.topnav.btnBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        fragmentAddCardsBinding.btnSaveCard.setOnClickListener {

            if (fragmentAddCardsBinding.etCardholdername.text.length==0){
                Toast.makeText(mainActivity, "Enter Card Holder Name", Toast.LENGTH_SHORT).show()

            }else if (fragmentAddCardsBinding.etCardnumber.text.toString().length<16){

                Toast.makeText(mainActivity, "Enter Valid Card Number", Toast.LENGTH_SHORT).show()

            }else if (fragmentAddCardsBinding.etExpdate.text.toString().length==0){

                Toast.makeText(mainActivity, "Enter Valid Exp. Date", Toast.LENGTH_SHORT).show()

            }else if (fragmentAddCardsBinding.etCvv.text.toString().length<3){

                Toast.makeText(mainActivity, "Enter Valid CVV", Toast.LENGTH_SHORT).show()


            }else{

//                addCard(fragmentAddCardsBinding.etCardholdername.text.toString(),
//                    fragmentAddCardsBinding.etCardnumber.text.toString(),
//                    fragmentAddCardsBinding.etExpdate.text.toString(),
//                    fragmentAddCardsBinding.etCvv.text.toString(), it)
            }


        }

    }


//    private fun addCard(cardholdername:String, cardnumber:String, expdate:String, cvv:String, view: View){
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//            viewModel.addpaymentcard(AddcardRequestModel(holder_name = cardholdername,
//            card_number = cardnumber, exp_date = expdate, cvv = cvv))
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                if (resource.data?.status == true) {
//                                    val builder = android.app.AlertDialog.Builder(mainActivity)
//                                    builder.setMessage(resource.data.message)
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        dialog.dismiss()
//                                        val navController = Navigation.findNavController(view)
//                                        navController.popBackStack()
//                                        //navController.navigate(R.id.nav_save_card)
//                                    }
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0 ->
//                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.orange))
//                                    }
//                                    alert.show()
//
//                                } else {
//
//                                    Toast.makeText(mainActivity, resource.data?.message, Toast.LENGTH_SHORT).show()
//
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
//            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show() }
//
//    }

}