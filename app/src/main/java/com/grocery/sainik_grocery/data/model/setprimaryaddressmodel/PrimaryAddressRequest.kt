package com.grocery.sainik_grocery.data.model.setprimaryaddressmodel


import com.google.gson.annotations.SerializedName

data class PrimaryAddressRequest(
    @SerializedName("CustomerId")
    val customerId: String,
    @SerializedName("houseNo")
    val houseNo: String,
    @SerializedName("Id")
    val id: String,
    @SerializedName("IsPrimary")
    val isPrimary: Boolean,
    @SerializedName("landMark")
    val landMark: String,
    @SerializedName("streetDetails")
    val streetDetails: String,
    @SerializedName("type")
    val type: String
)