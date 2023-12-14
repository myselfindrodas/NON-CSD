package com.grocery.sainik_grocery.data.model.supportmodel


import com.google.gson.annotations.SerializedName

data class SupportResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)