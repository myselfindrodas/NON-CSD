package com.grocery.sainik_grocery.data.model.updatecartmodel


import com.google.gson.annotations.SerializedName

data class CartUpdateResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)