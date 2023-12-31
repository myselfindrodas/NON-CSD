package com.grocery.sainik_grocery.ui

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grocery.sainik_grocery.BuildConfig
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.ActivityMainBinding
import com.grocery.sainik_grocery.ui.fragment.HomeFragment.Companion.cartCount
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject


class MainActivity : BaseActivity(),
    NavController.OnDestinationChangedListener,
    PaymentResultWithDataListener,
    ExternalWalletListener  {

    lateinit var activityMainBinding: ActivityMainBinding
    var orderid = ""
    var refid = ""
    private lateinit var viewModel: CommonViewModel
    lateinit var bottomNavView: BottomNavigationView

    var ivImgProfile: ImageView? = null
    var tvUserName: TextView? = null
    var tvlettersBuyer: TextView? = null
    var tvappVersion: TextView? = null
    var tvEditProfile: TextView? = null
    var tvUrcName: TextView? = null
    var tvChangeURC: TextView? = null
    var btnNavhome: LinearLayout?= null
    var btnExpandaccount: LinearLayout?= null
    var btnMyaccountExpand: ImageView? = null
    var btnMyaccountClose: ImageView? = null
    var llmyaccountsubmenu: LinearLayout? = null
    var btnNavprofile: LinearLayout? = null
    var btnNavorders: LinearLayout? = null
    var btnNavwishlist: LinearLayout? = null
    var btnNavsaveaddress: LinearLayout? = null
    var btnNavsavecard: LinearLayout? = null
    var btnNavCategory: LinearLayout?= null
    var btnNavsupport: LinearLayout? = null
    var btnNavnotification: LinearLayout? = null
    var btnNavfaq: LinearLayout? = null
    var btnNavlogout: LinearLayout?= null
    var btnNavChangeOption: LinearLayout?= null
    var btnAddress: LinearLayout?= null
    var versionName = ""

    var mNavController: NavController? = null
    override fun resourceLayout(): Int {
        return R.layout.activity_main

    }

    companion object{
        var URCName = ""
        var payment = ""
        var cartcount = ""
    }
    override fun initializeBinding(binding: ViewDataBinding) {


        this.activityMainBinding = binding as ActivityMainBinding
    }

    override fun setFunction() {
        Checkout.preload(this)

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        tvlettersBuyer = findViewById(R.id.tvlettersBuyer)
        tvUserName = findViewById(R.id.tvUserName)
        tvEditProfile = findViewById(R.id.tvEditProfile)
        tvUrcName = findViewById(R.id.tvUrcName1)
        tvChangeURC = findViewById(R.id.tvChangeURC)
        btnNavhome = findViewById(R.id.btnNavhome)
        btnExpandaccount = findViewById(R.id.btnExpandaccount)
        btnMyaccountExpand = findViewById(R.id.btnMyaccountExpand)
        btnMyaccountClose = findViewById(R.id.btnMyaccountClose)
        llmyaccountsubmenu = findViewById(R.id.llmyaccountsubmenu)
        btnNavprofile = findViewById(R.id.btnNavprofile)
        btnNavorders = findViewById(R.id.btnNavorders)
        btnNavwishlist = findViewById(R.id.btnNavwishlist)
        btnNavsaveaddress = findViewById(R.id.btnNavsaveaddress)
        btnNavsavecard = findViewById(R.id.btnNavsavecard)
        btnNavCategory = findViewById(R.id.btnNavCategory)
        btnNavsupport = findViewById(R.id.btnNavsupport)
        btnNavnotification = findViewById(R.id.btnNavnotification)
        btnNavfaq = findViewById(R.id.btnNavfaq)
        btnNavlogout = findViewById(R.id.btnNavlogout)
        btnNavChangeOption = findViewById(R.id.btnNavChangeOption)
        btnAddress = findViewById(R.id.btnAddress)
        tvappVersion = findViewById(R.id.tvappVersion)

//        tvUrcName?.text = URCName.replaceFirstChar { char ->
//            char.uppercaseChar()
//        }

        tvUrcName?.text = LocationActivity.locationAddress

//        profileget()

        val drawerLayout: DrawerLayout = activityMainBinding.drawerLayout

        tvEditProfile?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_profile)
        }

        tvChangeURC?.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            startActivity(Intent(this, URCListActivity::class.java))
            // mainActivity.finish()
        }

        btnNavhome?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_home)
            bottomNavView.selectedItemId = R.id.nav_home
        }

        btnExpandaccount?.setOnClickListener {

            btnMyaccountClose?.visibility = View.VISIBLE
            llmyaccountsubmenu?.visibility = View.VISIBLE
            btnMyaccountExpand?.visibility = View.GONE

        }

        btnMyaccountExpand?.setOnClickListener {

            btnMyaccountClose?.visibility = View.VISIBLE
            llmyaccountsubmenu?.visibility = View.VISIBLE
            btnMyaccountExpand?.visibility = View.GONE

        }

        btnMyaccountClose?.setOnClickListener {

            btnMyaccountExpand?.visibility = View.VISIBLE
            btnMyaccountClose?.visibility = View.GONE
            llmyaccountsubmenu?.visibility = View.GONE

        }

        btnAddress?.setOnClickListener {



        }

        /*fragmentHomeBinding.btnCurrentLocaion.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            *//*val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_productlist)*//*
        }*/

        btnNavprofile?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_profile)
        }

        btnNavorders?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_myorder)
            bottomNavView.selectedItemId = R.id.nav_myorder

        }

        btnNavwishlist?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_wishlist)
        }
        btnNavsavecard?.visibility  =  View.GONE
        btnNavsavecard?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_save_card)
        }

        btnNavsaveaddress?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_address)
        }

        btnNavCategory?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            val bundle = Bundle()
            bundle.putString("viewalltype", "category")
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_productlist, bundle)
            bottomNavView.selectedItemId = R.id.nav_category
        }

        btnNavsupport?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_support)
        }

        btnNavnotification?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_notification)
        }

        btnNavfaq?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            mNavController!!.navigateUp()
            mNavController!!.navigate(R.id.nav_faq)
        }


        btnNavChangeOption?.setOnClickListener {

            drawerLayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, ShopFromActivity::class.java)
            startActivity(intent)
            finish()

        }

        btnNavlogout?.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you want to logout?")
            builder.setPositiveButton(
                "Ok"
            ) { dialog, which ->
                Shared_Preferences.setLoginStatus(false)
                Shared_Preferences.clearPref()
                val intent = Intent(this, SplashScreenActivity::class.java)
                startActivity(intent)
                finishAffinity()
                dialog.cancel()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            val alert = builder.create()
            alert.setOnShowListener { arg0: DialogInterface? ->
                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.orange, resources.newTheme()))
            }
            alert.show()
        }

        mNavController = findNavController(R.id.flFragment)

        mNavController?.addOnDestinationChangedListener(this)
        mNavController?.navigate(R.id.nav_home)

        bottomNavView = findViewById(R.id.bottomNavigationView)
        bottomNavView.setupWithNavController(mNavController!!)

        tvUserName?.text = Shared_Preferences.getName()



        bottomNavView.setOnItemSelectedListener {item->
            when(item.itemId){
                R.id.nav_home -> {
                    mNavController!!.navigateUp()
                    mNavController!!.navigate(R.id.nav_home)
                    true
                }
                R.id.nav_category -> {
                    val bundle = Bundle()
                    bundle.putString("viewalltype", "category")
                    mNavController!!.navigateUp()
                    mNavController!!.navigate(R.id.nav_productlist, bundle)
                    true
                }
                R.id.nav_canteen -> {
                    startActivity(Intent(this, URCListActivity::class.java))
                    true
                }
                R.id.nav_myorder -> {
                    mNavController!!.navigateUp()
                    mNavController!!.navigate(R.id.nav_myorder)
                    true
                }

                R.id.nav_basket -> {
                    mNavController!!.navigateUp()
                    mNavController!!.navigate(R.id.nav_cart)
                    true
                }

                else -> false
            }

        }

        Log.d(TAG, "cartcount-->"+cartCount)

        val versionCode: Int = BuildConfig.VERSION_CODE
        versionName = BuildConfig.VERSION_NAME
        tvappVersion?.text = "Version : "+versionName
        versioncheck()







    }



    private fun versioncheck(){
        if (Utilities.isNetworkAvailable(this)) {
            viewModel.appversion().observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                this.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {
                                        for (i in 0 until itResponse.data.size) {
                                            if (itResponse.data[i].appType.equals("Android")) {
                                                val versioncurrent = itResponse.data[i].version
                                                if (versioncurrent.toDouble()>versionName.toDouble()){

                                                    val builder = AlertDialog.Builder(this)
                                                    builder.setCancelable(false)
                                                    builder.setMessage("New Updated Version Available please download latest version of the app")
                                                    builder.setPositiveButton(
                                                        "Continue"
                                                    ) { dialog, which ->
                                                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maitricomplex.in/download"))
                                                        startActivity(browserIntent)
                                                        finish()
                                                        dialog.cancel()
                                                    }

                                                    builder.setNegativeButton("Cancel") { dialog, which ->
                                                        dialog.cancel()
                                                        finishAffinity()
                                                    }
                                                    val alert = builder.create()
                                                    alert.setOnShowListener { arg0: DialogInterface? ->
                                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                                                            .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
                                                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                                            .setTextColor(resources.getColor(R.color.orange, resources.newTheme()))
                                                    }
                                                    alert.show()

                                                }

                                            }

                                        }


                                    } else {

                                        Toast.makeText(
                                            this,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }


                            }

                            Status.ERROR -> {
                                this.hideProgressDialog()
                                val builder =
                                    androidx.appcompat.app.AlertDialog.Builder(this)
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
                                this.showProgressDialog()
                            }

                        }

                    }
                }

        } else {
            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()
        }

    }





    fun showProgressDialog() {
        activityMainBinding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        activityMainBinding.rlLoading.visibility = View.GONE

    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

        hideKeyboard()

    }

    override fun onDestroy() {
        findNavController(R.id.flFragment).removeOnDestinationChangedListener(this)
        super.onDestroy()
    }

    fun setBottomNavigationVisibility(isVisible:Boolean=false){
        if (isVisible){
            activityMainBinding.contentMain.bottomNavigationView.visibility = View.VISIBLE
        }else{
            activityMainBinding.contentMain.bottomNavigationView.visibility = View.GONE

        }
    }

    fun startPayment(amount:String) {
        val co = Checkout()
        co.setKeyID("rzp_test_uurwJaTvkiwPsI")
      //  co.setKeyID("rzp_test_QgKM8E1RJQWaGB")
       // co.setKeyID("rzp_live_8Tu7NHdgOVlTDY")

        val totalamount: Double = amount.toDouble() * 100
//        orderid = orderId
//        refid = refId

        try {
            val options = JSONObject()
            options.put("name","Sainik Grocery Payment")
            options.put("description","Order Charges")
            options.put("image",R.mipmap.ic_launcher)
            options.put("theme.color", "#0F385A")
            options.put("currency","INR")
//            options.put("order_id", "order_DBJOWzybf0sJbb")
            options.put("amount",totalamount.toString())
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(this,options)

        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }


    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, p0, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "paymentid-->"+p1?.data)
        payment = "success"
//        orderpayment(orderid, p1?.paymentId.toString(), refid)


    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Error-->"+p1)
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {

        Toast.makeText(this, p0, Toast.LENGTH_SHORT).show()

//        orderpayment(orderid, p1?.paymentId.toString(), refid)


    }




    fun openDrawer() {
        val drawer = activityMainBinding.drawerLayout
        drawer.openDrawer(GravityCompat.START,true)
    }

}