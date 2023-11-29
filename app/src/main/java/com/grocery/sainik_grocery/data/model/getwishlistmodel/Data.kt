package com.grocery.sainik_grocery.data.model.getwishlistmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("product")
    val product: Product,
    @SerializedName("productId")
    val productId: String
)