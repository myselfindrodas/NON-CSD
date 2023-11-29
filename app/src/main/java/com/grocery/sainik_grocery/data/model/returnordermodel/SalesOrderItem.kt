package com.grocery.sainik_grocery.data.model.returnordermodel


import com.google.gson.annotations.SerializedName

data class SalesOrderItem(
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("discountPercentage")
    val discountPercentage: Int,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("salesOrderItemTaxes")
    val salesOrderItemTaxes: List<SalesOrderItemTaxe>,
    @SerializedName("taxValue")
    val taxValue: Int,
    @SerializedName("unitId")
    val unitId: String,
    @SerializedName("unitPrice")
    val unitPrice: Int,
    @SerializedName("warehouseId")
    val warehouseId: String
)