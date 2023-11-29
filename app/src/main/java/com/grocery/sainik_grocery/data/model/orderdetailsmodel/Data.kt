package com.grocery.sainik_grocery.data.model.orderdetailsmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("customer")
    val customer: Customer,
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("customerName")
    val customerName: Any?,
    @SerializedName("deliveryAddress")
    val deliveryAddress: String,
    @SerializedName("deliveryAddressId")
    val deliveryAddressId: String,
    @SerializedName("deliveryAddresses")
    val deliveryAddresses: List<Any>,
    @SerializedName("deliveryCharges")
    val deliveryCharges: Double,
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
    @SerializedName("paymentStatus")
    val paymentStatus: Int,
    @SerializedName("salesOrderItems")
    val salesOrderItems: List<SalesOrderItem>,
    @SerializedName("salesOrderPayments")
    val salesOrderPayments: List<Any>,
    @SerializedName("soCreatedDate")
    val soCreatedDate: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("termAndCondition")
    val termAndCondition: String,
    @SerializedName("totalAmount")
    val totalAmount: Double,
    @SerializedName("totalDiscount")
    val totalDiscount: Double,
    @SerializedName("totalPaidAmount")
    val totalPaidAmount: Double,
    @SerializedName("totalTax")
    val totalTax: Double
)