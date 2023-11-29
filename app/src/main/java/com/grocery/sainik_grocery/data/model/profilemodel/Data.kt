package com.grocery.sainik_grocery.data.model.profilemodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("cityId")
    val cityId: Any?,
    @SerializedName("cityName")
    val cityName: String,
    @SerializedName("contactPerson")
    val contactPerson: String,
    @SerializedName("countryId")
    val countryId: Any?,
    @SerializedName("countryName")
    val countryName: String,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("customerProfile")
    val customerProfile: Any?,
    @SerializedName("description")
    val description: String,
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
    val password: String,
    @SerializedName("phoneNo")
    val phoneNo: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("website")
    val website: String
)