package com.grocery.sainik_grocery.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.ActivityMainBinding
import com.grocery.sainik_grocery.databinding.ActivityWelcomeBinding
import com.grocery.sainik_grocery.ui.adapter.BannerAdapter
import com.grocery.sainik_grocery.ui.adapter.LoginBannerAdapter
import com.grocery.sainik_grocery.utils.PrefManager
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class Welcome : BaseActivity() {

    lateinit var binding:ActivityWelcomeBinding
    private lateinit var viewModel: CommonViewModel
    private var loginBannerAdapter:LoginBannerAdapter?=null
    private var prefManager: PrefManager? = null


    override fun resourceLayout(): Int {
        return R.layout.activity_welcome
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityWelcomeBinding

    }

    override fun setFunction() {

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        prefManager = PrefManager(this)
        if (!prefManager!!.isFirstTimeLaunch) {
            launchHomeScreen()
            finish()
        }

        loginBannerAdapter= LoginBannerAdapter(this)
        binding.welcomeBanner.setAdapter(loginBannerAdapter)
        binding.dotsIndicatorTop.attachTo(binding.welcomeBanner)


        binding.btnNext.setOnClickListener {

            launchHomeScreen()

        }

        Banners()

    }



    private fun Banners(){

        if (Utilities.isNetworkAvailable(this)) {
            viewModel.LoginPageBanners().observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()

                            if (resource.data?.status == true) {


                                loginBannerAdapter?.updateList(resource.data.data)



                            } else {
                                Toast.makeText(
                                    this,
                                    resource.data?.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            val builder = android.app.AlertDialog.Builder(this)
                            builder.setMessage(it.data?.message)
                            builder.setPositiveButton(
                                "Ok"
                            ) { dialog, which ->

                                dialog.cancel()

                            }
                            val alert = builder.create()
                            alert.setOnShowListener { arg0 ->
                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(resources.getColor(R.color.blue))
                            }
                            alert.show()


                        }

                        Status.LOADING -> {
                            showProgressDialog()
                        }
                    }
                }
            }

        } else {
            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()
        }

    }


    private fun launchHomeScreen() {
        prefManager!!.isFirstTimeLaunch = false
        startActivity(Intent(this@Welcome, LoginActivity::class.java))
        finish()
    }


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE

    }

}