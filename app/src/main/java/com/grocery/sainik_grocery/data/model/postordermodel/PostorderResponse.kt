package com.grocery.sainik_grocery.data.model.postordermodel


import com.google.gson.annotations.SerializedName

data class PostorderResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)