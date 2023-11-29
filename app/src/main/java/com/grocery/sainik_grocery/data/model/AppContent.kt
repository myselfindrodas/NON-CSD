package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName

data class AppContent(
    @SerializedName("created_at")
    val createdAt: String?, // null
    @SerializedName("deleted_at")
    val deletedAt: String?, // null
    @SerializedName("description")
    val description: String, // Receive notification relat to order status, payment and support Aut haec tibi, Torquate, sunt vituperanda aut patrocinium voluptatis repudian dum. Quod si ita se habeat, non possit beatam praestare vitam sapientia.
    @SerializedName("id")
    val id: Int, // 8
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("title")
    val title: String, // How to Check status of My Order
    @SerializedName("type")
    val type: Int, // 1
    @SerializedName("updated_at")
    val updatedAt: String?, // null
    var isExpanded: Boolean=false // null
)