package com.grocery.sainik_grocery.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.sainikgrocerycustomer.data.model.Addres
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
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.addressaddmodel.AddressAddRequest
import com.grocery.sainik_grocery.data.model.addressaddmodel.AddressEditRequest
import com.grocery.sainik_grocery.data.model.addresslistmodel.Data
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentAddAddressBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class AddAddressFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentAddAddressBinding: FragmentAddAddressBinding
    private lateinit var viewModel: CommonViewModel
    private var addressType = ""

    private var mLastLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var locationRequest: LocationRequest? = null
    var locationManager: LocationManager? = null
    var latitude: String? = ""
    var longitude: String? = ""
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    var placesClient: PlacesClient? = null

    var addressData: Data? = null
    var isEdited = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAddAddressBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_address, container, false)
        val root = fragmentAddAddressBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm
        val intent = arguments
        if (intent != null && intent.containsKey("viewtype")) {
            addressData = getDataSerializable(intent, "data", Data::class.java)
            println(addressData!!.houseNo)
            isEdited = true
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity)

        if (!Places.isInitialized()) {
            Places.initialize(mainActivity, getString(R.string.api_key))
        }
        placesClient = Places.createClient(mainActivity)


        locationManager = mainActivity.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager

        with(fragmentAddAddressBinding) {

            topnav.tvNavtitle.text = "Add address"

            btnSearchLocation.setOnClickListener {
                openSearchBar()
            }

            if (addressData != null) {
                addressData?.let { itAddressData ->
                    etHouseNo.setText(itAddressData.houseNo)
                    etLandmark.setText(itAddressData.landMark)
//                    etApartmentName.setText(itAddressData.apartmentName)
//                    etAreaDetails.setText(itAddressData.area)
                    etStreeDetails.setText(itAddressData.streetDetails)
//                    etCity.setText(itAddressData.city)
//                    etPinCode.setText(itAddressData.pincode)
                    btnAddAddress.text = "Edit Address"
                    topnav.tvNavtitle.text = "Edit address"
                    when (itAddressData.type) {
                        "Home" -> {
                            llenteraddress.visibility = View.GONE
                            changeTypeBackground(this, btnAddresshome)

                            addressType = "Home"
                        }
                        "Office" -> {
                            llenteraddress.visibility = View.GONE
                            changeTypeBackground(this, btnAddressoffice)
                            addressType = "Office"
                        }
                        else -> {
                            llenteraddress.visibility = View.VISIBLE
                            changeTypeBackground(this, btnOther)

                            etOtherType.setText(itAddressData.type)
                            addressType = "Other"
                        }
                    }

                }
            }



            if (HomeFragment.cartCount > 0) {
                topnav.tvCartCount.text = HomeFragment.cartCount.toString()
                topnav.cvCartCount.visibility = View.VISIBLE
            } else {
                topnav.cvCartCount.visibility = View.GONE
            }

            topnav.clCart.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_cart)


            }
            topnav.btnBack.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                mainActivity.onBackPressedDispatcher.onBackPressed()
            }
            if (HomeFragment.cartCount > 0) {
                topnav.tvCartCount.text = HomeFragment.cartCount.toString()
                topnav.cvCartCount.visibility = View.VISIBLE
            } else {
                topnav.cvCartCount.visibility = View.GONE
            }

            topnav.ivNotification.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_notification)

            }


            topnav.clCart.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_cart)

            }

            topnav.btnWishlist.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_wishlist)


            }

            btnAddresshome.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                llenteraddress.visibility = View.GONE
                changeTypeBackground(this, btnAddresshome)

                addressType = "Home"

            }


            btnAddressoffice.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                llenteraddress.visibility = View.GONE
                changeTypeBackground(this, btnAddressoffice)
                addressType = "Office"

            }

            btnOther.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                llenteraddress.visibility = View.VISIBLE
                changeTypeBackground(this, btnOther)
                addressType = "Other"

            }

            btnAddAddress.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                if (!validate(fragmentAddAddressBinding)) {
                    return@setOnClickListener
                }
                if (addressType == "Other")
                    addressType = etOtherType.text.toString().trim()




                if (isEdited) {
                    postEditAddress(it)
                } else {
                    postAddAddress(it)
                }
            }

        }
    }


    private fun openSearchBar() {

        val fields = listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(mainActivity)
        resolutionForPlaceResult.launch(intent)
        //startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }


    private fun displayLocationSettingsRequest(context: Context) {
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
                val status: com.google.android.gms.common.api.Status = result.status
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
                                if (location == null)
                                    Toast.makeText(
                                        mainActivity,
                                        "Cannot get location.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    mLastLocation = location
                                    val lat = location.latitude
                                    val lon = location.longitude
                                    latitude = location.latitude.toString()
                                    longitude = location.longitude.toString()
                                    reverseGeocoding(latitude!!, longitude!!)
//                                    Toast.makeText(
//                                        mainActivity,
//                                        "Lat: $lat  Long: $lon",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//
//                                    Shared_Preferences.setLat(lat.toString())
//                                    Shared_Preferences.setLong(lon.toString())
                                    mainActivity.hideProgressDialog()


//                                    reverseGeocoding(
//                                        location.latitude.toString(),
//                                        location.longitude.toString()
//                                    )

                                    //fusedLocationClient.removeLocationUpdates(location)
                                    Log.i(
                                        "TAG",
                                        "Lat: $lat  Long: $lon"
                                    )
                                }

                            }
                    }
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
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

        if (activityResult.resultCode == Activity.RESULT_OK)
            displayLocationSettingsRequest(mainActivity)
    }


    private val resolutionForPlaceResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->

        if (activityResult.resultCode == Activity.RESULT_OK) {

            val place = Autocomplete.getPlaceFromIntent(activityResult.data!!)
            val address = place.address

            fragmentAddAddressBinding.btnSearchLocation.text = address
            val lati = place.latLng!!.latitude.toString() + ""
            val longi = place.latLng!!.longitude.toString() + ""
            latitude = lati
            longitude = longi

            reverseGeocoding(latitude!!, longitude!!)

            /*  val lat = location.latitude
              val lon = location.longitude*/
//            Toast.makeText(
//                mainActivity,
//                "Lat: $lati  Long: $longi",
//                Toast.LENGTH_SHORT
//            ).show()

//            Shared_Preferences.setLat(lati)
//            Shared_Preferences.setLong(longi)
            /*activityRegistrationDetailsBinding.etLocation.setText(address)*/

        } else if (activityResult.resultCode == AutocompleteActivity.RESULT_ERROR) {

            val status = Autocomplete.getStatusFromIntent(activityResult.data!!)
        } else if (activityResult.resultCode == Activity.RESULT_CANCELED) {
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
            displayLocationSettingsRequest(mainActivity)

        } else {
            // PERMISSION NOT GRANTED
        }
    }


    private fun reverseGeocoding(lat: String, long: String) {
        var formatted_address = ""
        if (formatted_address.isNotEmpty()) {
            return
        }
        if (Utilities.isNetworkAvailable(mainActivity)) {
            mainActivity.showProgressDialog()
            val jsonRequest: JsonObjectRequest = object : JsonObjectRequest(
                Method.GET,
                "https://maps.googleapis.com/maps/api/geocode/json?" +
                        "latlng=" + lat + "," + long + "&key=AIzaSyA2mtyhq14pKHoTX0JMCqyTd1oxVrnr3fE",
                null,
                Response.Listener { response: JSONObject ->
                    Log.i("reverseGeoResponse-->", response.toString())
                    try {
                        val result = JSONObject(response.toString())
                        val status = result.getString("status")
                        val responseArray = result.getJSONArray("results")

                        val maxLogSize = 1000
                        for (i in 0..responseArray.toString().length / maxLogSize) {
                            val start = i * maxLogSize
                            var end = (i + 1) * maxLogSize
                            end =
                                if (end > responseArray.toString().length) responseArray.toString().length else end
                            // Log.v("TAG", responseArray.toString().substring(start, end))
                            Log.i(
                                "reverseGeoResponse11-->",
                                responseArray.toString().substring(start, end)
                            )
                        }
                        if (status == "OK") {
                            //for (i in 0 until responseArray.length()) {
                            val resultsobj = responseArray.getJSONObject(0)
                            formatted_address = resultsobj.getString("formatted_address")
                            val address_components = resultsobj.getJSONArray("address_components")
                            val buildingNoJson = address_components.getJSONObject(0)
                            val buildingNo1Json = address_components.getJSONObject(1)
                            val streetJson = address_components.getJSONObject(2)
                            val areaJson = address_components.getJSONObject(3)
                            val cityJson = address_components.getJSONObject(4)
                            val districtJson = address_components.getJSONObject(5)
                            val stateJson = address_components.getJSONObject(7)
                            val zipcodeJson = address_components.getJSONObject(address_components.length()-1)


                            val buildingNo=buildingNoJson.getString("long_name")+" "+buildingNo1Json.getString("long_name")
                            val street=streetJson.getString("long_name")
                            val area=areaJson.getString("long_name")
                            val city=cityJson.getString("long_name")
                            val district=districtJson.getString("long_name")
                            val state=stateJson.getString("long_name")
                            val zipcode=zipcodeJson.getString("long_name")

                            println("$buildingNo -  $street - $area - $city - $district - $state - $zipcode")


                            fragmentAddAddressBinding.etStreeDetails.setText(street+" , "+district)



                        } else {
                            mainActivity.hideProgressDialog()
                            Toast.makeText(mainActivity, "invalid", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    mainActivity.hideProgressDialog()
                },
                Response.ErrorListener { error ->
                    try {

                        val statusCode = error.networkResponse.statusCode
                        Log.e(ContentValues.TAG, "statuscode-->$statusCode")
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    mainActivity.hideProgressDialog()

                }) {
            }
            Volley.newRequestQueue(mainActivity).add(jsonRequest)
        } else {
            Toast.makeText(
                mainActivity,
                "Ooops! Internet Connection Error",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun changeTypeBackground(
        mFragmentAddAddressBinding: FragmentAddAddressBinding,
        button: AppCompatButton
    ) {

        with(mFragmentAddAddressBinding) {
            btnAddresshome.setBackgroundResource(R.drawable.rounded_orange_border)
            btnAddressoffice.setBackgroundResource(R.drawable.rounded_orange_border)
            btnOther.setBackgroundResource(R.drawable.rounded_orange_border)
            btnAddresshome.setTextColor(ContextCompat.getColor(mainActivity, R.color.orange))
            btnAddressoffice.setTextColor(ContextCompat.getColor(mainActivity, R.color.orange))
            btnOther.setTextColor(ContextCompat.getColor(mainActivity, R.color.orange))
            button.setTextColor(ContextCompat.getColor(mainActivity, R.color.white))
            button.setBackgroundResource(R.drawable.rounded_orange_bg_btn)

        }
    }

    private fun <T : Serializable?> getDataSerializable(
        @Nullable bundle: Bundle?,
        @Nullable key: String?,
        clazz: Class<T>
    ): T? {
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                return bundle.getSerializable(key, clazz)
            } else {
                try {
                    return bundle.getSerializable(key) as T?
                } catch (ignored: Throwable) {
                }
            }
        }
        return null
    }

    private fun validate(mFragmentAddAddressBinding: FragmentAddAddressBinding): Boolean {
        with(mFragmentAddAddressBinding) {

            if (latitude.toString().isEmpty() && latitude.toString().isEmpty()){
                Toast.makeText(mainActivity, "Select Address for latitude and longitude", Toast.LENGTH_SHORT).show()
                btnSearchLocation.requestFocus()
                return false
            }else if (etHouseNo.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter House no.", Toast.LENGTH_SHORT).show()
                etHouseNo.requestFocus()
                return false
            } else if (etLandmark.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Apertment name.", Toast.LENGTH_SHORT).show()
                etLandmark.requestFocus()
                return false
            } else if (etStreeDetails.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Street details.", Toast.LENGTH_SHORT).show()
                etHouseNo.requestFocus()
                return false
            } else if (addressType.isEmpty()) {
                Toast.makeText(
                    mainActivity,
                    "Choose a nickname for this address",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else if (addressType.equals("Other", true) && etOtherType.text.toString().isEmpty()) {
                Toast.makeText(
                    mainActivity,
                    "Enter a nickname for this address",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else {
                return true
            }
        }
    }

    private fun postAddAddress(view: View) {


        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.addressadd(
                AddressAddRequest(
                    Shared_Preferences.getUserId(),
                    fragmentAddAddressBinding.etHouseNo.text.toString().trim(),
                    false,
                    fragmentAddAddressBinding.etLandmark.text.toString().trim(),
                    fragmentAddAddressBinding.etStreeDetails.text.toString().trim(),
                    addressType,
                    latitude.toString(),
                    longitude.toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                if (resource.data?.status == true) {

                                    val builder = android.app.AlertDialog.Builder(mainActivity)
                                    builder.setMessage(resource.data.message)
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        dialog.dismiss()

                                        val navController = Navigation.findNavController(view)
                                        navController.popBackStack()
                                        //navController.navigate(R.id.nav_address)
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
    private fun postEditAddress(view: View) {


        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.editaddress(
                id = addressData!!.id,
                AddressEditRequest(
                    Shared_Preferences.getUserId(),
                    addressData!!.id,
                    fragmentAddAddressBinding.etHouseNo.text.toString().trim(),
                    false,
                    fragmentAddAddressBinding.etLandmark.text.toString().trim(),
                    fragmentAddAddressBinding.etStreeDetails.text.toString().trim(),
                    addressType,
                    latitude.toString(),
                    longitude.toString()
                ))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                if (resource.data?.status == true) {

                                    val builder = android.app.AlertDialog.Builder(mainActivity)
                                    builder.setMessage(resource.data.message)
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        dialog.dismiss()
                                        val navController = Navigation.findNavController(view)
                                        navController.navigate(R.id.nav_address)

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