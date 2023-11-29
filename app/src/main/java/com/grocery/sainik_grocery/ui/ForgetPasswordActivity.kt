package com.grocery.sainik_grocery.ui

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.ActivityForgetPasswordBinding
import com.grocery.sainik_grocery.databinding.ActivityLoginBinding
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class ForgetPasswordActivity : BaseActivity() {


    lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var viewModel: CommonViewModel


    override fun resourceLayout(): Int {
        return R.layout.activity_forget_password
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityForgetPasswordBinding
    }

    override fun setFunction() {
        with(binding){


            val vm: CommonViewModel by viewModels {
                CommonModelFactory(ApiHelper(ApiClient.apiService))
            }

            viewModel = vm

            svForgetLayout.visibility=View.VISIBLE
            svGotoLogin.visibility=View.GONE
            btnSubmit.setOnClickListener {

                if (etEmail.text.toString().length==0){
                    Toast.makeText(applicationContext, "Enter Email Address", Toast.LENGTH_LONG).show()
                }else{
//                    forgotpassword(etEmail.text.toString())
                }


            }

            btnGo.setOnClickListener {
                startActivity(Intent(this@ForgetPasswordActivity,LoginActivity::class.java))
                finish()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.svGotoLogin.isVisible){
            binding.svForgetLayout.visibility=View.VISIBLE
            binding.svGotoLogin.visibility=View.GONE

        }else {
            onBackPressedDispatcher.onBackPressed()
        }
    }



//    private fun forgotpassword(email:String){
//
//        if (Utilities.isNetworkAvailable(this)) {
//
//            viewModel.forgotpassword(ForgetPassRequestModel(email = email)).observe(this) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            hideProgressDialog()
//                            if (resource.data?.status==true){
//                                if (binding.svForgetLayout.isVisible){
//                                    binding.svForgetLayout.visibility=View.GONE
//                                    binding.svGotoLogin.visibility=View.VISIBLE
//                                }
//                            }else{
//
//                                val builder = AlertDialog.Builder(this@ForgetPasswordActivity)
//                                builder.setMessage(it.data?.msg)
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.setOnShowListener { arg0 ->
//                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                        .setTextColor(resources.getColor(R.color.orange))
//                                }
//                                alert.show()
//
//                            }
//
//
//                        }
//                        Status.ERROR -> {
//                            hideProgressDialog()
//                            Log.d(ContentValues.TAG, "print-->"+ resource.data?.status)
//
//                        }
//
//                        Status.LOADING -> {
//                            showProgressDialog()
//                        }
//
//                    }
//
//                }
//            }
//
//
//
//        }else{
//
//            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
//
//        }
//
//
//    }



    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }

}