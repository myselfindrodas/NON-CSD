package com.grocery.sainik_grocery.data.model.getcartlistmodel


import com.google.gson.annotations.SerializedName

data class CartData(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("discount")
    val discount: Double,
    @SerializedName("discountPercentage")
    val discountPercentage: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("isAdvanceOrderRequest")
    val isAdvanceOrderRequest: Boolean,
    @SerializedName("product")
    val product: Product,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("taxValue")
    val taxValue: Double,
    @SerializedName("total")
    val total: Double,
    @SerializedName("unitId")
    val unitId: String,
    @SerializedName("unitName")
    val unitName: String,
    @SerializedName("unitPrice")
    val unitPrice: Double
)