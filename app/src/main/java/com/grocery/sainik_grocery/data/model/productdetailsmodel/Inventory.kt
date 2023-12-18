package com.grocery.sainik_grocery.data.model.productdetailsmodel


import com.google.gson.annotations.SerializedName

data class Inventory(
    @SerializedName("averagePurchasePrice")
    val averagePurchasePrice: Double,
    @SerializedName("averageSalesPrice")
    val averageSalesPrice: Double,
    @SerializedName("brandId")
    val brandId: String,
    @SerializedName("brandName")
    val brandName: Any?,
    @SerializedName("discount")
    val discount: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("inventorySource")
    val inventorySource: Int,
    @SerializedName("pricePerUnit")
    val pricePerUnit: Double,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("purchaseOrderId")
    val purchaseOrderId: Any?,
    @SerializedName("salesOrderId")
    val salesOrderId: Any?,
    @SerializedName("stock")
    val stock: Double,
    @SerializedName("taxValue")
    val taxValue: Double,
    @SerializedName("unitId")
    val unitId: String,
    @SerializedName("unitName")
    val unitName: Any?,
    @SerializedName("warehouseId")
    val warehouseId: Any?
)