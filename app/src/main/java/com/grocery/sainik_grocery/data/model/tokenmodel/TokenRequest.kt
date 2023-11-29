package com.grocery.sainik_grocery.data.model.tokenmodel


import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("remoteIp")
    val remoteIp: String,
    @SerializedName("userName")
    val userName: String
)