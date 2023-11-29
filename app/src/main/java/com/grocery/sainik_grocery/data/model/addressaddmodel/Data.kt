package com.grocery.sainik_grocery.data.model.addressaddmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("customerName")
    val customerName: Any?,
    @SerializedName("houseNo")
    val houseNo: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("isPrimary")
    val isPrimary: Boolean,
    @SerializedName("landMark")
    val landMark: String,
    @SerializedName("streetDetails")
    val streetDetails: String,
    @SerializedName("type")
    val type: String
)