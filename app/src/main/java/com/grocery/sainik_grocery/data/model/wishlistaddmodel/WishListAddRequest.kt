package com.grocery.sainik_grocery.data.model.wishlistaddmodel


import com.google.gson.annotations.SerializedName

data class WishListAddRequest(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("productId")
    val productId: String
)