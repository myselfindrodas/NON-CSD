package com.grocery.sainik_grocery.data.model.orderdetailsmodel


import com.google.gson.annotations.SerializedName

data class OrderdetailsRequest(
    @SerializedName("Id")
    val id: String,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("skip")
    val skip: Int
)