package com.grocery.sainik_grocery.data.model.ordersummerymodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("deliveryCharges")
    val deliveryCharges: String,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("items")
    val items: String,
    @SerializedName("price")
    val price: String
)