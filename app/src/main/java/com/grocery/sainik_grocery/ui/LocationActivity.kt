package com.grocery.sainik_grocery.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.tasks.*
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.databinding.ActivityLocationBinding
import com.grocery.sainik_grocery.utils.Shared_Preferences
import org.json.JSONObject


class LocationActivity : BaseActivity() {

    lateinit var binding: ActivityLocationBinding

    private var mLastLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var locationRequest:LocationRequest?=null
    private var mRequestQueue: RequestQueue? = null
    private val mStringRequest: StringRequest? = null
    override fun resourceLayout(): Int {
        return R.layout.activity_location
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityLocationBinding
    }

    override fun setFunction() {
        with(binding) {

            fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this@LocationActivity)

            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            btnSearchLocation.setOnClickListener {

                startActivity(Intent(this@LocationActivity,GetLocationActivity::class.java))
            }
            btnCurrentLocaion.setOnClickListener {

                if (ContextCompat.checkSelfPermission(
                        this@LocationActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                } else {
                    displayLocationSettingsRequest(this@LocationActivity)
                }
            }

            if (ContextCompat.checkSelfPermission(
                    this@LocationActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            } else {
                // displayLocationSettingsRequest(this@LocationActivity)
            }
        }
    }


    private fun displayLocationSettingsRequest(context: Context) {
        binding.rlLoading.visibility=View.VISIBLE
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()
        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.interval = 10000
        locationRequest!!.fastestInterval = 10000 / 2
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)
        val resultPending: PendingResult<LocationSettingsResult> =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        resultPending.setResultCallback(object : ResultCallback<LocationSettingsResult?> {
            @SuppressLint("MissingPermission")
            override fun onResult(result: LocationSettingsResult) {
                val status: Status = result.status
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> {
                        Log.i(
                            "TAG",
                            "All location settings are satisfied."
                        )

                        fusedLocationClient.getCurrentLocation(
                            Priority.PRIORITY_HIGH_ACCURACY,
                            object : CancellationToken() {
                                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                                    CancellationTokenSource().token

                                override fun isCancellationRequested() = false
                            })
                            .addOnSuccessListener { location: Location? ->

                                binding.rlLoading.visibility=View.GONE
                                if (location == null)
                                    Toast.makeText(
                                        this@LocationActivity,
                                        "Cannot get location.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    mLastLocation = location
                                    val lat = location.latitude
                                    val lon = location.longitude
                                    /*Toast.makeText(
                                        this@LocationActivity,
                                        "Lat: $lat  Long: $lon",
                                        Toast.LENGTH_SHORT
                                    ).show()*/

//                                    val intent = Intent(this@LocationActivity, MainActivity::class.java)
                                    Shared_Preferences.setLat(lat.toString())
                                    Shared_Preferences.setLong(lon.toString())

                                    reverseGeocoding(
                                        location.latitude.toString(),
                                        location.longitude.toString()
                                    )

                                    //fusedLocationClient.removeLocationUpdates(location)
                                    Log.i(
                                        "TAG",
                                        "Lat: $lat  Long: $lon"
                                    )
                                }

                            }
                    }
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {

                        binding.rlLoading.visibility=View.GONE
                        Log.i(
                            "TAG",
                            "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                        )
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            val intentSenderRequest = IntentSenderRequest
                                .Builder(status.resolution!!).build()
                            resolutionForResult.launch(intentSenderRequest)
                            /*status.startResolutionForResult(
                                this@LocationActivity,
                                11
                            )*/
                        } catch (e: SendIntentException) {
                            Log.i("TAG", "PendingIntent unable to execute request.")
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                        "TAG",
                        "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                    )
                }
            }
        })
    }

    private val resolutionForResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->

        if (activityResult.resultCode == RESULT_OK)
            displayLocationSettingsRequest(this@LocationActivity)
    }


    @SuppressLint("MissingPermission")
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            displayLocationSettingsRequest(this)

        } else {
            // PERMISSION NOT GRANTED
        }
    }

    companion object{
        var locationAddress=""
    }
    fun reverseGeocoding(lat: String, long: String) {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        try {
            val `object` = JSONObject()
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                "https://maps.googleapis.com/maps/api/geocode/json?" + "latlng=" + lat + "," + long + "&key=AIzaSyCGRQavtVfIlnBuSkELe98R2MFjXQdnLRc",
                null,
                { itResponse ->
                    val result = JSONObject(itResponse.toString())
                    val status = result.getString("status")
                    val responseArray =
                        result.getJSONArray("results")
                    if (status == "OK") {
                        for (i in 0 until responseArray.length()) {
                            val resultsobj = responseArray.getJSONObject(4)
                            val formatted_address = resultsobj.getString("formatted_address")
                            locationAddress=formatted_address
                            val intent = Intent(this@LocationActivity, ShopFromActivity::class.java)
                            startActivity(intent)

                            // Toast.makeText(this, formatted_address, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            ) { Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show() }
            requestQueue.add(jsonObjectRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
