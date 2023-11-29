package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName

data class DataNotification(
    @SerializedName("created_at")
    val createdAt: Any?, // null
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("description")
    val description: String, // test
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("title")
    val title: String, // test
    @SerializedName("type")
    val type: Int, // 1
    @SerializedName("type_id")
    val typeId: Int, // 1
    @SerializedName("updated_at")
    val updatedAt: Any?, // null
    @SerializedName("user_id")
    val userId: Int // 2
)