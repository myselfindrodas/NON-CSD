package com.grocery.sainik_grocery.data.model.ordersummerymodel


import com.google.gson.annotations.SerializedName

data class OrdersummeryResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)