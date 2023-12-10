package com.grocery.sainik_grocery.data.model.postordermodel


import com.google.gson.annotations.SerializedName

data class PostOrderRequest(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("productMainCategoryId")
    val productMainCategoryId: String,
    @SerializedName("isAppOrderRequest")
    val isAppOrderRequest: Boolean,
    @SerializedName("customerName")
    val customerName: String?,
    @SerializedName("deliveryAddress")
    val deliveryAddress: String,
    @SerializedName("DeliveryAddressId")
    val deliveryAddressId: String,
    @SerializedName("DeliveryCharges")
    val deliveryCharges: String,
    @SerializedName("deliveryDate")
    val deliveryDate: String,
    @SerializedName("deliveryStatus")
    val deliveryStatus: String,
    @SerializedName("isSalesOrderRequest")
    val isSalesOrderRequest: Boolean,
    @SerializedName("note")
    val note: String,
    @SerializedName("orderNumber")
    val orderNumber: String,
    @SerializedName("paymentStatus")
    val paymentStatus: String,
    @SerializedName("salesOrderItems")
    val salesOrderItems: List<SalesOrderItem>,
    @SerializedName("soCreatedDate")
    val soCreatedDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("termAndCondition")
    val termAndCondition: String,
    @SerializedName("totalAmount")
    val totalAmount: String,
    @SerializedName("totalDiscount")
    val totalDiscount: String,
    @SerializedName("totalPaidAmount")
    val totalPaidAmount: String,
    @SerializedName("totalTax")
    val totalTax: String
)