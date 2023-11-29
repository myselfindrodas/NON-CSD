package com.grocery.sainik_grocery.data.model.tokenmodel


import com.google.gson.annotations.SerializedName

data class Claim(
    @SerializedName("claimType")
    val claimType: String,
    @SerializedName("claimValue")
    val claimValue: String
)