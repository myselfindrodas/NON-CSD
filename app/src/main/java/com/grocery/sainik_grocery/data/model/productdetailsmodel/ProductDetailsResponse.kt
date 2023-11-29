package com.grocery.sainik_grocery.data.model.productdetailsmodel


import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)