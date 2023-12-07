package com.grocery.sainik_grocery.data.model.postordermodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SalesOrderItem(
    @SerializedName("customer")
    val customer: String?,
    @SerializedName("customerName")
    val customerName: String?,
    @SerializedName("discount")
    val discount: Double,
    @SerializedName("discountPercentage")
    val discountPercentage: Double,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("salesOrderItemTaxes")
    val salesOrderItemTaxes: List<Any>,
    @SerializedName("salesOrderNumber")
    val salesOrderNumber: String?,
    @SerializedName("soCreatedDate")
    val soCreatedDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("taxValue")
    val taxValue: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("unitConversation")
    val unitConversation: UnitConversation,
    @SerializedName("unitId")
    val unitId: String,
    @SerializedName("unitName")
    val unitName: String?,
    @SerializedName("unitPrice")
    val unitPrice: String,
    @SerializedName("warehouseId")
    val warehouseId: String,
    @SerializedName("warehouseName")
    val warehouseName: String?
):Serializable