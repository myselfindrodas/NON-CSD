package com.grocery.sainik_grocery.data.model.newordernumbermodel


import com.google.gson.annotations.SerializedName

data class NewOrderNumberResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("orderNumber")
    val orderNumber: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)