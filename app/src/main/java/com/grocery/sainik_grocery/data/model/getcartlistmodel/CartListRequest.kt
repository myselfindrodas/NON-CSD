package com.grocery.sainik_grocery.data.model.getcartlistmodel


import com.google.gson.annotations.SerializedName

data class CartListRequest(
    @SerializedName("CustomerId")
    val customerId: String,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("skip")
    val skip: Int
)