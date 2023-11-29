package com.grocery.sainik_grocery.data.model.ordersummerymodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("deliveryCharges")
    val deliveryCharges: Double,
    @SerializedName("discount")
    val discount: Double,
    @SerializedName("items")
    val items: Double,
    @SerializedName("price")
    val price: Double
)