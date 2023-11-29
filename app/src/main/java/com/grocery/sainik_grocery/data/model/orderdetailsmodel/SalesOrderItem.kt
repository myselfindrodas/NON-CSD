package com.grocery.sainik_grocery.data.model.orderdetailsmodel


import com.google.gson.annotations.SerializedName

data class SalesOrderItem(
    @SerializedName("customer")
    val customer: Any?,
    @SerializedName("customerName")
    val customerName: Any?,
    @SerializedName("discount")
    val discount: Double,
    @SerializedName("discountPercentage")
    val discountPercentage: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("product")
    val product: Product,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("salesOrderId")
    val salesOrderId: String,
    @SerializedName("salesOrderItemTaxes")
    val salesOrderItemTaxes: List<Any>,
    @SerializedName("salesOrderNumber")
    val salesOrderNumber: Any?,
    @SerializedName("soCreatedDate")
    val soCreatedDate: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("taxValue")
    val taxValue: Double,
    @SerializedName("total")
    val total: Double,
    @SerializedName("unitConversation")
    val unitConversation: UnitConversation,
    @SerializedName("unitId")
    val unitId: String,
    @SerializedName("unitName")
    val unitName: Any?,
    @SerializedName("unitPrice")
    val unitPrice: Double,
    @SerializedName("warehouseId")
    val warehouseId: Any?,
    @SerializedName("warehouseName")
    val warehouseName: Any?
)