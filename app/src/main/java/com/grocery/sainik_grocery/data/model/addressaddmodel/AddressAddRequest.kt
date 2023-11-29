package com.grocery.sainik_grocery.data.model.addressaddmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddressAddRequest(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("houseNo")
    val houseNo: String,
    @SerializedName("IsPrimary")
    val isPrimary: Boolean,
    @SerializedName("landMark")
    val landMark: String,
    @SerializedName("streetDetails")
    val streetDetails: String,
    @SerializedName("type")
    val type: String
):Serializable