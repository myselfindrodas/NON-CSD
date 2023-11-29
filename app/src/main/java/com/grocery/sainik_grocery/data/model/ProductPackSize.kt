package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName

data class ProductPackSize(
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("price")
    val price: Double, // 45
    @SerializedName("selling_price")
    val sellingPrice: Double, // 40
    @SerializedName("size")
    val size: String, // 500gm
    @SerializedName("urc_product_id")
    val urcProductId: Int, // 2
    @SerializedName("is_primary")
    var is_primary: Int // 1
)