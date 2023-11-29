package com.grocery.sainik_grocery.data.model.categorymodel


import com.google.gson.annotations.SerializedName

data class CategoryListResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)