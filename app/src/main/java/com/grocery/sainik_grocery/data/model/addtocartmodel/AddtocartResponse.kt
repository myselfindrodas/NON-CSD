package com.grocery.sainik_grocery.data.model.addtocartmodel


import com.google.gson.annotations.SerializedName

data class AddtocartResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)