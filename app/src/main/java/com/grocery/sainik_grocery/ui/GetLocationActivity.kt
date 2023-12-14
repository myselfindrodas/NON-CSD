package com.grocery.sainik_grocery.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.databinding.ActivityGetLocationBinding
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Utilities
import org.json.JSONObject
import java.util.*

class GetLocationActivity : BaseActivity() {

    lateinit var binding: ActivityGetLocationBinding
    private var mLastLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var locationRequest: LocationRequest? = null
    var locationManager: LocationManager? = null
    var latitude: String? = ""
    var longitude: String? = ""
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    var placesClient: PlacesClient? = null

    override fun resourceLayout(): Int {
        return R.layout.activity_get_location
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityGetLocationBinding

    }

    override fun setFunction() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this@GetLocationActivity)

        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.api_key))
        }
        placesClient = Places.createClient(this)


        locationManager = getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager


        binding.ivBack.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnCurrentLocation.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            if (ContextCompat.checkSelfPermission(
                    this@GetLocationActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            } else {
                displayLocationSettingsRequest(this@GetLocationActivity)
            }
        }
        binding.btnSearchLocation.setOnClickListener {
            openSearchBar()
        }
    }

    private fun openSearchBar() {

        val fields = listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        resolutionForPlaceResult.launch(intent)
        //startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    private fun displayLocationSettingsRequest(context: Context) {

        binding.rlLoading.visibility= View.VISIBLE
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
                                        this@GetLocationActivity,
                                        "Cannot get location.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    mLastLocation = location
                                    val lat = location.latitude
                                    val lon = location.longitude
                                    /*Toast.makeText(
                                        this@GetLocationActivity,
                                        "Lat: $lat  Long: $lon",
                                        Toast.LENGTH_SHORT
                                    ).show()*/

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
                        } catch (e: IntentSender.SendIntentException) {
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
            displayLocationSettingsRequest(this@GetLocationActivity)
    }


    private val resolutionForPlaceResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->

        if (activityResult.resultCode == RESULT_OK) {

            val place = Autocomplete.getPlaceFromIntent(activityResult.data!!)
            val address = place.address

            binding.btnSearchLocation.text = address
            val lati = place.latLng!!.latitude.toString() + ""
            val longi = place.latLng!!.longitude.toString() + ""
            latitude = lati
            longitude = longi

            LocationActivity.locationAddress = address
          /*  val lat = location.latitude
            val lon = location.longitude*/
//            Toast.makeText(
//                this@GetLocationActivity,
//                "Lat: $lati  Long: $longi",
//                Toast.LENGTH_SHORT
//            ).show()

            Shared_Preferences.setLat(lati)
            Shared_Preferences.setLong(longi)
//            val intent = Intent(
//                this@GetLocationActivity,
//                URCListActivity::class.java
//            )
//            startActivity(intent)


            val intent = Intent(
                this@GetLocationActivity,
                ShopFromActivity::class.java
            )
            startActivity(intent)
            /*activityRegistrationDetailsBinding.etLocation.setText(address)*/

        } else if (activityResult.resultCode == AutocompleteActivity.RESULT_ERROR) {
// TODO: Handle the error.
            val status = Autocomplete.getStatusFromIntent(activityResult.data!!)
        } else if (activityResult.resultCode == RESULT_CANCELED) {
// The user canceled the operation.
        }
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
                            LocationActivity.locationAddress =formatted_address
//
//                            val intent = Intent(
//                                this@GetLocationActivity,
//                                URCListActivity::class.java
//                            )
//                            startActivity(intent)



                            val intent = Intent(
                                this@GetLocationActivity,
                                ShopFromActivity::class.java
                            )
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