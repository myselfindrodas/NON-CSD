package com.grocery.sainik_grocery.data.model.orderlistmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("customer")
    val customer: Any?,
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("deliveryDate")
    val deliveryDate: String,
    @SerializedName("deliveryStatus")
    val deliveryStatus: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("isSalesOrderRequest")
    val isSalesOrderRequest: Boolean,
    @SerializedName("note")
    val note: Any?,
    @SerializedName("orderNumber")
    val orderNumber: String,
    @SerializedName("paymentStatus")
    val paymentStatus: Int,
    @SerializedName("deliveryAddress")
    val deliveryAddress: String,
    @SerializedName("deliveryAddressId")
    val deliveryAddressId: String,
    @SerializedName("salesOrderItems")
    val salesOrderItems: Any?,
    @SerializedName("salesOrderPayments")
    val salesOrderPayments: Any?,
    @SerializedName("soCreatedDate")
    val soCreatedDate: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("termAndCondition")
    val termAndCondition: Any?,
    @SerializedName("totalAmount")
    val totalAmount: String,
    @SerializedName("paymentType")
    val paymentType: String,
    @SerializedName("totalDiscount")
    val totalDiscount: Double,
    @SerializedName("totalPaidAmount")
    val totalPaidAmount: Double,
    @SerializedName("totalTax")
    val totalTax: Double
):Serializable