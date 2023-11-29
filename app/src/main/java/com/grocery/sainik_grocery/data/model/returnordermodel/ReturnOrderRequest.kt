package com.grocery.sainik_grocery.data.model.returnordermodel


import com.google.gson.annotations.SerializedName

data class ReturnOrderRequest(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("deliveryDate")
    val deliveryDate: String,
    @SerializedName("deliveryStatus")
    val deliveryStatus: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("isSalesOrderRequest")
    val isSalesOrderRequest: Boolean,
    @SerializedName("note")
    val note: String,
    @SerializedName("orderNumber")
    val orderNumber: String,
    @SerializedName("salesOrderItems")
    val salesOrderItems: List<SalesOrderItem>,
    @SerializedName("salesOrderStatus")
    val salesOrderStatus: Int,
    @SerializedName("soCreatedDate")
    val soCreatedDate: String,
    @SerializedName("totalAmount")
    val totalAmount: Int,
    @SerializedName("totalDiscount")
    val totalDiscount: Int,
    @SerializedName("totalTax")
    val totalTax: Int
)