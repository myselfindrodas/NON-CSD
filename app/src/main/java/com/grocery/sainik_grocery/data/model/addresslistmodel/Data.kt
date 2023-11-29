package com.grocery.sainik_grocery.data.model.addresslistmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("houseNo")
    val houseNo: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("isPrimary")
    var isPrimary: Boolean,
    @SerializedName("landMark")
    val landMark: String,
    @SerializedName("streetDetails")
    val streetDetails: String,
    @SerializedName("type")
    val type: String
):Serializable