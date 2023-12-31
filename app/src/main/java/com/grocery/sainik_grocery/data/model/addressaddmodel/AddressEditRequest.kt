package com.grocery.sainik_grocery.data.model.addressaddmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddressEditRequest(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("houseNo")
    val houseNo: String,
    @SerializedName("IsPrimary")
    val isPrimary: Boolean,
    @SerializedName("landMark")
    val landMark: String,
    @SerializedName("streetDetails")
    val streetDetails: String,
    @SerializedName("Pincode")
    val Pincode: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("Latitude")
    val Latitude: String,
    @SerializedName("Longitutde")
    val Longitutde: String
):Serializable