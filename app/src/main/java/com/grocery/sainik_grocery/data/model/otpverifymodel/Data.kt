package com.grocery.sainik_grocery.data.model.otpverifymodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: Any?,
    @SerializedName("cityId")
    val cityId: Any?,
    @SerializedName("cityName")
    val cityName: Any?,
    @SerializedName("contactPerson")
    val contactPerson: String,
    @SerializedName("countryId")
    val countryId: Any?,
    @SerializedName("countryName")
    val countryName: Any?,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("customerProfile")
    val customerProfile: Any?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("email")
    val email: String,
    @SerializedName("fax")
    val fax: Any?,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: Any?,
    @SerializedName("isImageUpload")
    val isImageUpload: Boolean,
    @SerializedName("isUnsubscribe")
    val isUnsubscribe: Boolean,
    @SerializedName("isVarified")
    val isVarified: Boolean,
    @SerializedName("isWalkIn")
    val isWalkIn: Boolean,
    @SerializedName("logo")
    val logo: Any?,
    @SerializedName("mobileNo")
    val mobileNo: String,
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("password")
    val password: Any?,
    @SerializedName("phoneNo")
    val phoneNo: Any?,
    @SerializedName("url")
    val url: Any?,
    @SerializedName("website")
    val website: String
)