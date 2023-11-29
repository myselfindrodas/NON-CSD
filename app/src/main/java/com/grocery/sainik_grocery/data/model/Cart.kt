package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("created_at")
    val createdAt: String, // 2023-04-05T12:14:58.000000Z
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("is_order")
    val isOrder: Int, // 0
    @SerializedName("order_id")
    val orderId: String?, // null
    @SerializedName("price")
    val price: Int, // 80
    @SerializedName("image")
    val image: Int, // null
    @SerializedName("name")
    val name: String, // shop
    @SerializedName("size")
    val size: String, // 500gm
    @SerializedName("product_id")
    val productId: Int, // 2
    @SerializedName("urc_product_id")
    val urc_product_id: Int, // 1
    @SerializedName("quantity")
    val quantity: Int, // 2
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-04-05T12:14:58.000000Z
    @SerializedName("user_id")
    val userId: Int // 2

)