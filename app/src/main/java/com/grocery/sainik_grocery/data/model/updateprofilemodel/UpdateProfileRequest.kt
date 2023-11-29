package com.grocery.sainik_grocery.data.model.updateprofilemodel


import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("address")
    val address: String,
    @SerializedName("cityId")
    val cityId: String?,
    @SerializedName("cityName")
    val cityName: String,
    @SerializedName("contactPerson")
    val contactPerson: String,
    @SerializedName("countryId")
    val countryId: String?,
    @SerializedName("countryName")
    val countryName: String,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("customerProfile")
    val customerProfile: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fax")
    val fax: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("isImageUpload")
    val isImageUpload: Boolean,
    @SerializedName("isUnsubscribe")
    val isUnsubscribe: Boolean,
    @SerializedName("isVarified")
    val isVarified: Boolean,
    @SerializedName("isWalkIn")
    val isWalkIn: Boolean,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("mobileNo")
    val mobileNo: String,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNo")
    val phoneNo: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("website")
    val website: String
)