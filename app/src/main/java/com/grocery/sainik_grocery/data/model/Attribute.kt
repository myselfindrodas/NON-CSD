package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName

data class Attribute(
    @SerializedName("created_at")
    val createdAt: String?, // null
    @SerializedName("deleted_at")
    val deletedAt: String?, // null
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("name")
    val name: String, // 1kg
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("updated_at")
    val updatedAt: String?, // null
    var isChecked:Boolean=false
)