package com.grocery.sainik_grocery.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.application.GlideApp
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.profilemodel.GetProfileRequest
import com.grocery.sainik_grocery.data.model.updateprofilemodel.UpdateProfileRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentProfileBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.utils.GetRealPathFromUri
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var fragmentProfileBinding: FragmentProfileBinding
    var isPasswordVisible = false
    var myCalendar = Calendar.getInstance()
    var selectdate: String? = ""
    var selectedgender: String? = ""
    var pathFromUri: String? = ""
    private var imageURL: String="https://maitricomplex.in/"
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val root = fragmentProfileBinding.root
        mainActivity = activity as MainActivity


        return root
    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentProfileBinding.topnav.tvNavtitle.text = "My Profile"


        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        fragmentProfileBinding.topnav.btnBack.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }

            mainActivity.onBackPressedDispatcher.onBackPressed()

        }


        fragmentProfileBinding.topnav.ivNotification.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)


        }

        fragmentProfileBinding.topnav.btnWishlist.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wishlist)

        }

        fragmentProfileBinding.Flimage.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            if (ContextCompat.checkSelfPermission(
                    mainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            } else {
                ImagePicker.Companion.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start()

            }

        }

        spgender()

        if (HomeFragment.cartCount>0) {
            fragmentProfileBinding.topnav.tvCartCount.text = HomeFragment.cartCount.toString()
            fragmentProfileBinding.topnav.cvCartCount.visibility=View.VISIBLE
        }else{
            fragmentProfileBinding.topnav.cvCartCount.visibility=View.GONE
        }
        fragmentProfileBinding.topnav.clCart.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)

        }


        fragmentProfileBinding.btnPassword.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            fragmentProfileBinding.llpassword.visibility = View.GONE
            fragmentProfileBinding.llchangepassword.visibility = View.VISIBLE
        }


        fragmentProfileBinding.oldpwdHideBtn.setOnClickListener {
            if (!isPasswordVisible) {
                fragmentProfileBinding.etOldPassword.transformationMethod = null
                fragmentProfileBinding.oldpwdHideBtn.setImageResource(R.drawable.ic_visibilityon)
                isPasswordVisible = true

            } else {
                fragmentProfileBinding.etOldPassword.transformationMethod =
                    PasswordTransformationMethod()
                fragmentProfileBinding.oldpwdHideBtn.setImageResource(R.drawable.ic_visibilityoff)
                isPasswordVisible = false
            }
            fragmentProfileBinding.etOldPassword.setSelection(fragmentProfileBinding.etOldPassword.length())
        }




        fragmentProfileBinding.newpwdHideBtn.setOnClickListener {
            if (!isPasswordVisible) {
                fragmentProfileBinding.etNewPassword.transformationMethod = null
                fragmentProfileBinding.newpwdHideBtn.setImageResource(R.drawable.ic_visibilityon)
                isPasswordVisible = true

            } else {
                fragmentProfileBinding.etNewPassword.transformationMethod =
                    PasswordTransformationMethod()
                fragmentProfileBinding.newpwdHideBtn.setImageResource(R.drawable.ic_visibilityoff)
                isPasswordVisible = false
            }
            fragmentProfileBinding.etNewPassword.setSelection(fragmentProfileBinding.etNewPassword.length())
        }




        fragmentProfileBinding.confirmpwdHideBtn.setOnClickListener {
            if (!isPasswordVisible) {
                fragmentProfileBinding.etConfirmPassword.transformationMethod = null
                fragmentProfileBinding.confirmpwdHideBtn.setImageResource(R.drawable.ic_visibilityon)
                isPasswordVisible = true

            } else {
                fragmentProfileBinding.etConfirmPassword.transformationMethod =
                    PasswordTransformationMethod()
                fragmentProfileBinding.confirmpwdHideBtn.setImageResource(R.drawable.ic_visibilityoff)
                isPasswordVisible = false
            }
            fragmentProfileBinding.etConfirmPassword.setSelection(fragmentProfileBinding.etConfirmPassword.length())
        }


        val selectdate =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                dateupdateLabel(myCalendar)
            }



        fragmentProfileBinding.etDOB.setOnClickListener {

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, 0)
//            calendar.add(Calendar.DATE, 0)
            val datePickerDialog = DatePickerDialog(
                mainActivity, selectdate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            myCalendar=calendar

            datePickerDialog.setOnShowListener { arg0 ->
                datePickerDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.orange))
                datePickerDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.orange))
            }
//            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()

        }



        fragmentProfileBinding.spGender.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    when (fragmentProfileBinding.spGender.getSelectedItem().toString()) {
                        "Male" -> selectedgender = "1 "
                        "Female" -> selectedgender = "2"
                        else -> selectedgender = ""
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })




        fragmentProfileBinding.btnUpdate.setOnClickListener {
            if (Utilities.isClickRecently()){
                return@setOnClickListener
            }
            when {
                fragmentProfileBinding.etFirstname.text.toString().length==0 -> {
                    Toast.makeText(mainActivity, "Please Enter full name", Toast.LENGTH_SHORT).show()
                }
//                fragmentProfileBinding.etLastname.text.toString().length==0 -> {
//                    Toast.makeText(mainActivity, "Please Enter last name", Toast.LENGTH_SHORT).show()
//                }
//                fragmentProfileBinding.etPhone.text.toString().length<10 -> {
//                    Toast.makeText(mainActivity, "Please Enter valid number", Toast.LENGTH_SHORT).show()
//                }
//                fragmentProfileBinding.etDOB.text.toString().length==0 -> {
//                    Toast.makeText(mainActivity, "Please Enter DOB", Toast.LENGTH_SHORT).show()
//                }
//                fragmentProfileBinding.llchangepassword.isVisible && fragmentProfileBinding.etOldPassword.text.toString().length>0 -> {
//
//                    when {
//                        fragmentProfileBinding.etNewPassword.text.toString().length==0 -> {
//                            Toast.makeText(mainActivity, "Please Enter New Password", Toast.LENGTH_SHORT).show()
//                        }
//                        fragmentProfileBinding.etConfirmPassword.text.toString().length==0 -> {
//                            Toast.makeText(mainActivity, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show()
//                        }
//                        !fragmentProfileBinding.etNewPassword.text.equals(fragmentProfileBinding.etConfirmPassword.text.toString()) -> {
//                            Toast.makeText(mainActivity, "Password not matched", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//                selectedgender?.length==0 -> {
//                    Toast.makeText(mainActivity, "Please Select Gender", Toast.LENGTH_SHORT).show()
//                }
                else -> {

                    when {
                        pathFromUri.equals("") -> {
                            updateprofile()
                        }
                        else -> {
//                            profilepicupload(pathFromUri.toString())

                        }
                    }

                }
            }
        }


        profileget()


    }


    private fun dateupdateLabel(calendar: Calendar) {
        val selecteddateformat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(selecteddateformat, Locale.US)
        selectdate = sdf.format(calendar.time)
        fragmentProfileBinding.etDOB.setText(selectdate)

    }


    @SuppressLint("MissingPermission")
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()

        } else {
            // PERMISSION NOT GRANTED
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        for (fragment in childFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }

        if (requestCode == 2404 && resultCode == Activity.RESULT_OK) {
            val fileUri = data!!.data

//            try {
//                Picasso.get()
//                    .load(fileUri)
//                    .error(R.drawable.noimagefound)
////                    .placeholder(R.drawable.loader_gif)
//                    .into(fragmentProfileBinding.PrfImg)
//
//                pathFromUri = GetRealPathFromUri.getPathFromUri(mainActivity, fileUri!!)!!
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(mainActivity, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mainActivity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun spgender() {
        val gender: MutableList<String> = ArrayList()
        gender.add("Select Gender")
        gender.add("Male")
        gender.add("Female")
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, gender)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragmentProfileBinding.spGender.setAdapter(arrayAdapter)
    }


    private fun updateprofile(){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.updateprofile(
                UpdateProfileRequest(address = "",
                    cityId = "",
                    cityName = "",
                    contactPerson = "",
                    countryId = "",
                    countryName = "",
                    customerName = fragmentProfileBinding.etFirstname.text.toString(),
                    customerProfile = "",
                    description = "",
                    email = "",
                    fax = "",
                    id = Shared_Preferences.getUserId(),
                    imageUrl = "",
                    isImageUpload = false,
                    isUnsubscribe = false,
                    isVarified = true,
                    isWalkIn = false,
                    logo = "",
                    mobileNo = fragmentProfileBinding.etPhone.text.toString(),
                    otp = "1234",
                    password = "123",
                    phoneNo = "",
                    url = "",
                    website = ""
                    )).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            resource.data?.let {itResponse->

                                if (itResponse.status) {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
                                    val navController = Navigation.findNavController(fragmentProfileBinding.root)
                                    navController.navigate(R.id.nav_home)
                                } else {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                }
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
                            alert.setOnShowListener { arg0 ->
                                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(resources.getColor(R.color.orange))
                                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                                    .setTextColor(resources.getColor(R.color.orange))
                            }
                            alert.show()
                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }


        }else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }
    }
//
//    private fun profilepicupload(pathFromUri: String){
//
//
//        if (Utilities.isNetworkAvailable(mainActivity)) {
//
//            val file = File(pathFromUri)
//            val fileReqBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
//            val part: MultipartBody.Part =
//                MultipartBody.Part.createFormData("image", file.name, fileReqBody)
//
//            val name: RequestBody =
//                RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etFirstname.text.toString())
//            val last_name: RequestBody =
//                RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etLastname.text.toString())
//            val phone: RequestBody =
//                RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etPhone.text.toString())
//            val gender: RequestBody =
//                RequestBody.create("text/plain".toMediaTypeOrNull(), selectedgender.toString())
//            val birthday: RequestBody =
//                RequestBody.create("text/plain".toMediaTypeOrNull(), selectdate.toString())
//
//            val password: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etConfirmPassword.text.toString()?:"")
//
//            val old_password: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etOldPassword.text.toString()?:"")
//
//
//            viewModel.profileupdate(
//                name,
//                last_name,
//                phone,
//                gender,
//                birthday,
//                password,
//                old_password,
//                part = part
//            ).observe(mainActivity) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            mainActivity.hideProgressDialog()
//                            resource.data?.let {itResponse->
//
//                                if (itResponse.status) {
//
//                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
//                                    try {
//                                        Picasso.get()
//                                            .load(resource.data.imageUrl+"/"+resource.data.data.user.image)
//                                            .error(R.drawable.noimagefound)
////                                            .placeholder(R.drawable.loader_gif)
//                                            .into(fragmentProfileBinding.PrfImg)
//
//                                    }catch (e:Exception){
//                                        e.printStackTrace()
//                                    }
//                                } else {
//
//                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
//
//                                }
//                            }
//
//
//                        }
//                        Status.ERROR -> {
//                            mainActivity.hideProgressDialog()
//                            val builder = AlertDialog.Builder(mainActivity)
//                            builder.setMessage(it.message)
//                            builder.setPositiveButton(
//                                "Ok"
//                            ) { dialog, which ->
//
//                                dialog.cancel()
//
//                            }
//                            val alert = builder.create()
//                            alert.setOnShowListener { arg0 ->
//                                alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                    .setTextColor(resources.getColor(R.color.orange))
//                                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
//                                    .setTextColor(resources.getColor(R.color.orange))
//                            }
//                            alert.show()
//                        }
//
//                        Status.LOADING -> {
//                            mainActivity.showProgressDialog()
//                        }
//
//                    }
//
//                }
//            }
//
//
//        }else {
//            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//
//
    private fun profileget(){

        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.getProfile(GetProfileRequest(id = Shared_Preferences.getUserId()))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {
                                        fragmentProfileBinding.etFirstname.setText(itResponse.data.customerName)
//                                        fragmentProfileBinding.etLastname.setText(itResponse.data.user.lastName)
                                        fragmentProfileBinding.etPhone.setText(itResponse.data.mobileNo)

                                        val inputString = itResponse.data.customerName?:""
                                        val splitName = inputString.split(" ")
                                        if (splitName.size >= 2) {
                                            val firstLetter = splitName[0].first()
                                            val lastLetter = splitName[1].first()
                                            val result = "$firstLetter$lastLetter"
                                            fragmentProfileBinding.tvlettersBuyer.text = result

                                        } else  {
                                            val firstLetter = splitName[0].first()
                                            val result = firstLetter
                                            fragmentProfileBinding.tvlettersBuyer.text = result.toString()
                                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                                        }


//                                        if (itResponse.data.user.gender==1){
//                                            fragmentProfileBinding.spGender.setSelection(1)
//                                        }else{
//                                            fragmentProfileBinding.spGender.setSelection(2)
//                                        }
//                                        fragmentProfileBinding.etDOB.setText(itResponse.data.user.birthday)
//                                        try {
//
//                                            Picasso.get()
//                                                .load(resource.data.imageUrl+"/"+itResponse.data.user.image)
//                                                .error(R.drawable.noimagefound)
////                                                .placeholder(R.drawable.loader_gif)
//                                                .into(fragmentProfileBinding.PrfImg)
//                                        }catch (e:Exception){
//                                            e.printStackTrace()
//                                        }

                                    } else {

                                        Toast.makeText(
                                            mainActivity,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }


                            }
                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                val builder = androidx.appcompat.app.AlertDialog.Builder(mainActivity)
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