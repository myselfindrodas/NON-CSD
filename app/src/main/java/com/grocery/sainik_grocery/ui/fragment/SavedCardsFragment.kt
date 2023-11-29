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
import com.example.sainikgrocerycustomer.data.model.PaymentCard
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentSavedCardsBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.SavedCardAdapter
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel


class SavedCardsFragment : Fragment(), SavedCardAdapter.OnItemClickListener {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentSavedCardBinding: FragmentSavedCardsBinding
    private var cardAdapter: SavedCardAdapter? = null
    private lateinit var viewModel: CommonViewModel
    private var orderId = ""
    private var amount = ""
    private var refid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSavedCardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_saved_cards, container, false)
        val root = fragmentSavedCardBinding.root
        mainActivity = activity as MainActivity


        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        val intent = arguments
        if (intent != null && intent.containsKey("orderId")) {
            orderId = intent.getString("orderId", "")
            println(orderId)
        }

        if (intent != null && intent.containsKey("refId")) {
            refid = intent.getString("refId", "")
            println(refid)
        }


        if (intent != null && intent.containsKey("price")) {
            amount = intent.getString("price", "")
            println(amount)
        }

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSavedCardBinding.topnav.tvNavtitle.text = "Cards"

        with(fragmentSavedCardBinding) {
            cardAdapter =
                SavedCardAdapter(mainActivity, this@SavedCardsFragment)
            rvCardList.adapter = cardAdapter
            rvCardList.layoutManager = GridLayoutManager(mainActivity, 1)
            val saveCardsList = ArrayList<PaymentCard>()
            for (i in 1..5){
                saveCardsList.add(PaymentCard("234221321","2023-04-07T08:51:38.000000Z", "23w", " ", "0000-00-00", "abc", 1, 1, 1, "2023-04-07T08:51:38.000000Z", 2))
            }
            cardAdapter!!.updateData(saveCardsList)

            btnAddnewcard.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_add_card)

            }
            topnav.btnBack.setOnClickListener {

                mainActivity.onBackPressedDispatcher.onBackPressed()
            }

            if (HomeFragment.cartCount > 0) {
                topnav.tvCartCount.text = HomeFragment.cartCount.toString()
                topnav.cvCartCount.visibility = View.VISIBLE
            } else {
                topnav.cvCartCount.visibility = View.GONE
            }

            topnav.ivNotification.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_notification)


            }
            topnav.clCart.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_cart)


            }

            topnav.btnWishlist.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_wishlist)

            }


            btnContinuepayment.setOnClickListener {

//                mainActivity.startPayment(orderId, amount, refid)
            }


            if (orderId.isEmpty()) {
                btnContinuepayment.visibility = View.GONE
            } else {
                btnContinuepayment.visibility = View.VISIBLE
            }
        }

//        getcardList()
    }


//    private fun getcardList() {
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//
//            viewModel.paymentcardlist()
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                resource.data?.let { itResponse ->
//
//                                    if (itResponse.status) {
//
//                                        cardAdapter?.updateData(itResponse.data.paymentCard)
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

    override fun onClick(
        position: Int,
        view: View,
        mSavedCardModelArrayList: ArrayList<PaymentCard>,
        isDelete: Boolean
    ) {

//        mSavedCardModelArrayList.forEach { item ->
//            item.isPrimary = 1
//        }
//        mSavedCardModelArrayList[position].isPrimary == 0
//        cardAdapter?.updateData(mSavedCardModelArrayList)

        if (isDelete) {
//            deleteDialog(mSavedCardModelArrayList[position])
        } else {
//            primarycard(mSavedCardModelArrayList[position].id.toString())

        }

    }


//    private fun primarycard(id: String) {
//
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//
//            viewModel.paymentcardPrimary(id)
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                resource.data?.let { itResponse ->
//
//                                    if (itResponse.status) {
//                                        getcardList()
//
//                                        Toast.makeText(
//                                            mainActivity,
//                                            itResponse.message,
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//
//
//                                    } else {
//
//                                        Toast.makeText(
//                                            mainActivity,
//                                            resource.data.message,
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
//
//
//    private fun deleteDialog(mSavedCardModelArrayList: PaymentCard) {
//        val builder = AlertDialog.Builder(mainActivity)
//        builder.setMessage("Are you sure you delete this card from the list?")
//        builder.setPositiveButton(
//            "Yes"
//        ) { dialog, which ->
//
//            deleteAddress(mSavedCardModelArrayList.id.toString())
//            dialog.dismiss()
//        }
//        builder.setNegativeButton(
//            "Cancel"
//        ) { dialog, which ->
//
//            dialog.dismiss()
//        }
//        val alert = builder.create()
//        alert.setOnShowListener { arg0 ->
//            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
//                .setTextColor(resources.getColor(R.color.orange))
//            alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
//                .setTextColor(resources.getColor(R.color.orange))
//        }
//        alert.show()
//    }
//
//
//    private fun deleteAddress(id: String) {
//
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//
//            viewModel.paymentcardDelete(id)
//                .observe(mainActivity) {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                mainActivity.hideProgressDialog()
//                                resource.data?.let { itResponse ->
//
//                                    if (itResponse.status) {
//                                        getcardList()
//                                        val builder = android.app.AlertDialog.Builder(mainActivity)
//                                        builder.setMessage(itResponse.message)
//                                        builder.setPositiveButton(
//                                            "Ok"
//                                        ) { dialog, which ->
//
//                                            dialog.dismiss()
//                                        }
//                                        val alert = builder.create()
//                                        alert.setOnShowListener { arg0 ->
//                                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
//                                                .setTextColor(resources.getColor(R.color.orange))
//                                        }
//                                        alert.show()
//
//                                    } else {
//
//                                        Toast.makeText(
//                                            mainActivity,
//                                            resource.data.message,
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


}