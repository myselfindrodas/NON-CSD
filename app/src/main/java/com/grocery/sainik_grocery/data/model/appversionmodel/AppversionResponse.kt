package com.grocery.sainik_grocery.data.model.appversionmodel


import com.google.gson.annotations.SerializedName

data class AppversionResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)