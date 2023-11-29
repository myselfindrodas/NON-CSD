package com.grocery.sainik_grocery.data.model.otpverifymodel


import com.google.gson.annotations.SerializedName

data class OtpverifyRequest(
    @SerializedName("MobileNo")
    val mobileNo: String,
    @SerializedName("otp")
    val otp: String
)