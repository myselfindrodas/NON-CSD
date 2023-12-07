package com.grocery.sainik_grocery.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.loginmodel.LoginRequest
import com.grocery.sainik_grocery.data.model.tokenmodel.TokenRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.ActivityLoginBinding
import com.grocery.sainik_grocery.ui.adapter.LoginBannerAdapter
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: CommonViewModel
    var isPasswordVisible = false
    private var loginBannerAdapter: LoginBannerAdapter?=null
    private val headerHandler = Handler()
    var currentPage = 0
    var delay = 2000
    var runnable: Runnable? = null
    //    private var mLastLocation: Location? = null
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    var locationRequest: LocationRequest? = null
//    var locationManager: LocationManager? = null
    var latitude: String? = ""
    var longitude: String? = ""

    override fun resourceLayout(): Int {
        return R.layout.activity_login
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityLoginBinding

    }

    override fun setFunction() {
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@LoginActivity)
//        locationManager = getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager


        with(binding){

            Shared_Preferences.setUserToken("")
            val vm: CommonViewModel by viewModels {
                CommonModelFactory(ApiHelper(ApiClient.apiService))
            }

            viewModel = vm

            loginBannerAdapter= LoginBannerAdapter(this@LoginActivity)
            welcomeBanner.setAdapter(loginBannerAdapter)
            dotsIndicatorTop.attachTo(welcomeBanner)



            btnLogin.setOnClickListener {

                if (binding.etUserName.text.toString().isEmpty()){
                    Toast.makeText(applicationContext, "Enter User Name", Toast.LENGTH_LONG).show()
                }
//                else if (binding.etPassword.text.toString().isEmpty()){
//                    Toast.makeText(applicationContext, "Enter Password", Toast.LENGTH_LONG).show()
//                }
            else{
                    loginapi()

//                    loginapi(binding.etUserName.text.toString(),
//                        binding.etPassword.text.toString())

//                    startActivity(Intent(this@LoginActivity,OtpActivity::class.java))

                }

            }


            tvForgetPass.setOnClickListener {
                startActivity(Intent(this@LoginActivity,ForgetPasswordActivity::class.java))
            }


            pwdHideBtn.setOnClickListener {
                if (!isPasswordVisible) {
                    etPassword.transformationMethod = null
                    pwdHideBtn.setImageResource(R.drawable.ic_visibilityon)
                    isPasswordVisible = true

                } else {
                    etPassword.transformationMethod =
                        PasswordTransformationMethod()
                    pwdHideBtn.setImageResource(R.drawable.ic_visibilityoff)
                    isPasswordVisible = false
                }
                etPassword.setSelection(etPassword.length())
            }
        }


//        if (ContextCompat.checkSelfPermission(
//                this@LoginActivity,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissionLauncher.launch(
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            )
//        } else {
//            displayLocationSettingsRequest(this@LoginActivity)
//        }

        generateToken()
        Banners()


    }



//    private fun displayLocationSettingsRequest(context: Context) {
//
//        binding.rlLoading.visibility= View.VISIBLE
//        val googleApiClient = GoogleApiClient.Builder(context)
//            .addApi(LocationServices.API).build()
//        googleApiClient.connect()
//        locationRequest = LocationRequest.create()
//        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        locationRequest!!.interval = 10000
//        locationRequest!!.fastestInterval = 10000 / 2
//        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
//        builder.setAlwaysShow(true)
//        val resultPending: PendingResult<LocationSettingsResult> =
//            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
//        resultPending.setResultCallback(object : ResultCallback<LocationSettingsResult?> {
//            @SuppressLint("MissingPermission")
//            override fun onResult(result: LocationSettingsResult) {
//                val status: com.google.android.gms.common.api.Status = result.status
//                when (status.statusCode) {
//                    LocationSettingsStatusCodes.SUCCESS -> {
//                        Log.i(
//                            "TAG",
//                            "All location settings are satisfied."
//                        )
//
//                        fusedLocationClient.getCurrentLocation(
//                            Priority.PRIORITY_HIGH_ACCURACY,
//                            object : CancellationToken() {
//                                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
//                                    CancellationTokenSource().token
//
//                                override fun isCancellationRequested() = false
//                            })
//                            .addOnSuccessListener { location: Location? ->
//
//                                binding.rlLoading.visibility=View.GONE
//                                if (location == null)
//                                    Toast.makeText(
//                                        this@LoginActivity,
//                                        "Cannot get location.",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                else {
//                                    mLastLocation = location
//                                    latitude = location.latitude.toString()
//                                    longitude = location.longitude.toString()
//                                    /*Toast.makeText(
//                                        this@GetLocationActivity,
//                                        "Lat: $lat  Long: $lon",
//                                        Toast.LENGTH_SHORT
//                                    ).show()*/
//
//
//                                    //fusedLocationClient.removeLocationUpdates(location)
//                                    Log.i(
//                                        "TAG",
//                                        "Lat: $latitude  Long: $longitude"
//                                    )
//                                }
//
//                            }
//                    }
//                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
//
//                        binding.rlLoading.visibility=View.GONE
//                        Log.i(
//                            "TAG",
//                            "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
//                        )
//                        try {
//                            // Show the dialog by calling startResolutionForResult(), and check the result
//                            // in onActivityResult().
//                            val intentSenderRequest = IntentSenderRequest
//                                .Builder(status.resolution!!).build()
//                            resolutionForResult.launch(intentSenderRequest)
//                            /*status.startResolutionForResult(
//                                this@LocationActivity,
//                                11
//                            )*/
//                        } catch (e: IntentSender.SendIntentException) {
//                            Log.i("TAG", "PendingIntent unable to execute request.")
//                        }
//                    }
//                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
//                        "TAG",
//                        "Location settings are inadequate, and cannot be fixed here. Dialog not created."
//                    )
//                }
//            }
//        })
//    }

//    private val resolutionForResult = registerForActivityResult(
//        ActivityResultContracts.StartIntentSenderForResult()
//    ) { activityResult ->
//
//        if (activityResult.resultCode == RESULT_OK)
//            displayLocationSettingsRequest(this@LoginActivity)
//    }
//
//
//
//    @SuppressLint("MissingPermission")
//    val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        val granted = permissions.entries.all {
//            it.value
//        }
//        if (granted) {
//            displayLocationSettingsRequest(this)
//
//        } else {
//            // PERMISSION NOT GRANTED
//        }
//    }



    private fun generateToken(){

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.generatetoken(TokenRequest(latitude = "22.5726459", longitude = "88.3638953",
                password = "admin@123", remoteIp = "", userName = "admin@gmail.com"))
                .observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                Shared_Preferences.setUserToken(resource.data?.bearerToken)
                                Log.d(TAG, "token-->"+resource.data?.bearerToken)


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






    private fun loginapi(){

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.login(LoginRequest(mobileNo = binding.etUserName.text.toString())).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.status==true){
                                val builder = AlertDialog.Builder(this@LoginActivity)
                                builder.setMessage(resource.data.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    Shared_Preferences.setEmail(resource.data.data.email)
                                    Shared_Preferences.setName(resource.data.data.customerName)
                                    Shared_Preferences.setPhoneNo(resource.data.data.mobileNo.toString())

                                    val intent = Intent(this, OtpActivity::class.java)
                                    intent.putExtra("otp", resource.data.data.otp.toString())
                                    intent.putExtra("phone", resource.data.data.mobileNo.toString())
                                    startActivity(intent)

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.orange))
                                }
                                alert.show()
                            }else{

                                val builder = AlertDialog.Builder(this@LoginActivity)
                                builder.setMessage(it.data?.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.orange))
                                }
                                alert.show()

                            }


                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->"+ resource.data?.status)
                            if (it.message!!.contains("400",true)) {
                                val builder = AlertDialog.Builder(this@LoginActivity)
                                builder.setMessage("Invalid Employee Id / Password")
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.orange))
                                }
                                alert.show()
                            }else
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                        }

                        Status.LOADING -> {
                            showProgressDialog()
                        }

                    }

                }
            }



        }else{

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }

    }


    override fun onResume() {
        super.onResume()

        headerHandler.postDelayed(Runnable {
            headerHandler.postDelayed(runnable!!, delay.toLong())
            if (currentPage === loginBannerAdapter!!.itemCount + 1 - 1) {
                currentPage = 0
            }

            binding.welcomeBanner.setCurrentItem(currentPage++, true)

        }.also { runnable = it }, delay.toLong())
        super.onResume()

    }


    override fun onPause() {
        super.onPause()
        headerHandler.removeCallbacks(runnable!!)
    }


    override fun onStop() {
        super.onStop()
        headerHandler.removeCallbacks(runnable!!)
    }


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }

}