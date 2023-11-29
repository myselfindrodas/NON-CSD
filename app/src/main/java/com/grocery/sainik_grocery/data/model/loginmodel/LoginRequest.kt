package com.grocery.sainik_grocery.data.model.loginmodel


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("MobileNo")
    val mobileNo: String
)