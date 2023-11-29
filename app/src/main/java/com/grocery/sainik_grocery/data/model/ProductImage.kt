package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName

data class ProductImage(
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("image")
    val image: String, // wqe
    @SerializedName("product_id")
    val productId: Int, // 2
    var isSelected: Boolean = false
)