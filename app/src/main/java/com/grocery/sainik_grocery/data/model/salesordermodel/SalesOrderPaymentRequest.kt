package com.grocery.sainik_grocery.data.model.salesordermodel


import com.google.gson.annotations.SerializedName

data class SalesOrderPaymentRequest(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("attachmentData")
    val attachmentData: String,
    @SerializedName("attachmentUrl")
    val attachmentUrl: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("paymentDate")
    val paymentDate: String,
    @SerializedName("paymentMethod")
    val paymentMethod: Int,
    @SerializedName("referenceNumber")
    val referenceNumber: String,
    @SerializedName("salesOrderId")
    val salesOrderId: String
)