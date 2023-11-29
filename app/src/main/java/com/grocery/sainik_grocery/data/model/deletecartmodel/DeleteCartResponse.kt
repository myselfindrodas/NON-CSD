package com.grocery.sainik_grocery.data.model.deletecartmodel


import com.google.gson.annotations.SerializedName

data class DeleteCartResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)