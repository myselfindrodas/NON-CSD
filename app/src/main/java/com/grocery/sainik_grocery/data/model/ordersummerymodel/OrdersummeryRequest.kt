package com.grocery.sainik_grocery.data.model.ordersummerymodel


import com.google.gson.annotations.SerializedName

data class OrdersummeryRequest(
    @SerializedName("CustomerId")
    val customerId: String
)