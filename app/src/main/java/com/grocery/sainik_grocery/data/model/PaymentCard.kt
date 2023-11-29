package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName

data class PaymentCard(
    @SerializedName("card_number")
    val cardNumber: String, // 234221321
    @SerializedName("created_at")
    val createdAt: String, // 2023-04-07T08:51:38.000000Z
    @SerializedName("cvv")
    val cvv: String, // 23w
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("exp_date")
    val expDate: String, // 0000-00-00
    @SerializedName("holder_name")
    val holderName: String, // abc
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("is_primary")
    var isPrimary: Int, // 1
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-04-07T08:51:38.000000Z
    @SerializedName("user_id")
    val userId: Int // 2
)