package com.grocery.sainik_grocery.data.model.wishlistaddmodel


import com.google.gson.annotations.SerializedName

data class WishlistAddResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)