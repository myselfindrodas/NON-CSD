package com.grocery.sainik_grocery.data.model.otpverifymodel


import com.google.gson.annotations.SerializedName

data class OtpVerifiedResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)