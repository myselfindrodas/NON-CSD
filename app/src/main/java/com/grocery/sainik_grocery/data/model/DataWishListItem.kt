package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName

data class DataWishListItem(
    @SerializedName("created_at")
    val createdAt: String, // 2023-04-12T10:07:18.000000Z
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("id")
    val id: Int, // 15
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-04-12T10:07:18.000000Z
    val productId: Int, // 2
    val discount: Double, // 12.5
    @SerializedName("urc_product_id")
    val urcProductId: Int, // 6
    @SerializedName("user_id")
    val userId: Int ,// 2
    val name: String, // shop
    val image: Any?, // null
    val unitsOfMeasurementTypes: String ,// Kg
    val sellingPrice: Int, // 77
    val price: Int // 88

)