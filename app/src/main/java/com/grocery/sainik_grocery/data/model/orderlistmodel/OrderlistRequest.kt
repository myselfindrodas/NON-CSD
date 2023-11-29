package com.grocery.sainik_grocery.data.model.orderlistmodel


import com.google.gson.annotations.SerializedName

data class OrderlistRequest(
    @SerializedName("CustomerId")
    val customerId: String,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("skip")
    val skip: Int
)