package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName

data class OrderHistory(
    @SerializedName("created_at")
    val createdAt: String, // 2023-04-11T06:02:53.000000Z
    @SerializedName("deleted_at")
    val deletedAt: String?, // null
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("order_id")
    val orderId: Int, // 4
    @SerializedName("price")
    val price: Double, // 132
    val name: String, // shop
    val image: String?, // null
    @SerializedName("product_id")
    val productId: Int, // 2
    val size: String ,// 100gm
    @SerializedName("product_pack_size_id")
    val productPackSizeId: Int, // 2
    @SerializedName("quantity")
    val quantity: Int, // 3
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("order_status")
    val order_status: Int, // 1
    @SerializedName("unit_price")
    val unitPrice: Double, // 44
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-04-11T06:02:53.000000Z
    @SerializedName("user_id")
    val userId: Int, // 2
    var isReturnNoteShown: Boolean=false // 2
)