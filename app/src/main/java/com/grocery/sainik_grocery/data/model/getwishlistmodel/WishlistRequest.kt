package com.grocery.sainik_grocery.data.model.getwishlistmodel


import com.google.gson.annotations.SerializedName

data class WishlistRequest(
    @SerializedName("customerId")
    val customerId: String
)