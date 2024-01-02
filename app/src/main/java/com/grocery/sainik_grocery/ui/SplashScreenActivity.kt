package com.grocery.sainik_grocery.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.tokenmodel.TokenRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.ActivitySplashBinding
import com.grocery.sainik_grocery.utils.AnimUtils
import com.grocery.sainik_grocery.utils.PrefManager
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class SplashScreenActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding
    private var prefManager: PrefManager? = null
    private lateinit var viewModel: CommonViewModel

    override fun resourceLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivitySplashBinding
    }

    override fun setFunction() {
//        Shared_Preferences.setUserToken("")
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        prefManager = PrefManager(this)
        generateToken()

    }

    private fun callNextScreen() {
        Handler(Looper.myLooper()!!).postDelayed({
            if (Shared_Preferences.getLoginStatus()==true){

                val intent = Intent(this, LocationActivity::class.java)
                startActivity(intent)
                AnimUtils.FadeOutAnim(this)
                finishAffinity()

            }else{

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                AnimUtils.FadeOutAnim(this)
                finishAffinity()
            }


        }, 3000)
    }





    private fun generateToken(){

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.generatetoken(
                TokenRequest(latitude = "22.5726459", longitude = "88.3638953",
                password = "admin@123", remoteIp = "", userName = "admin@gmail.com")
            )
                .observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                Shared_Preferences.setUserToken(resource.data?.bearerToken)
                                Log.d(ContentValues.TAG, "token-->"+resource.data?.bearerToken)
                                callNextScreen()


                            }

                            Status.ERROR -> {
                                hideProgressDialog()
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                                Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                                if (it.message!!.contains("400", true)) {
//                                    val builder = AlertDialog.Builder(this@LoginActivity)
//                                    builder.setMessage("Invalid Employee Id / Password")
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        dialog.cancel()
//
//                                    }
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0 ->
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.orange))
//                                    }
//                                    alert.show()
//                                } else
//                                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                            }

                            Status.LOADING -> {
                                showProgressDialog()
                            }

                        }

                    }
                }


        } else {

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }

    }


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }

}