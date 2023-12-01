package com.grocery.sainik_grocery.data.model.addtocartmodel


import com.google.gson.annotations.SerializedName

data class AddtocartRequest(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("isAdvanceOrderRequest")
    val isAdvanceOrderRequest: Boolean,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("discountPercentage")
    val discountPercentage: String,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("taxValue")
    val taxValue: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("unitId")
    val unitId: String,
    @SerializedName("unitName")
    val unitName: String,
    @SerializedName("unitPrice")
    val unitPrice: String
)