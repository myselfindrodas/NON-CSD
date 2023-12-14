package com.grocery.sainik_grocery.data.model.salesordermodel


import com.google.gson.annotations.SerializedName

data class SalesOrderPaymentResponse(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("attachmentUrl")
    val attachmentUrl: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("orderNumber")
    val orderNumber: Any?,
    @SerializedName("paymentDate")
    val paymentDate: String,
    @SerializedName("paymentMethod")
    val paymentMethod: Int,
    @SerializedName("referenceNumber")
    val referenceNumber: String,
    @SerializedName("salesOrderId")
    val salesOrderId: String
)