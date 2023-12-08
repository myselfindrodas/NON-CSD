package com.grocery.sainik_grocery.data.model.productmaincategorymodel


import com.google.gson.annotations.SerializedName

data class ProductmaincategoryResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)