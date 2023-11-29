package com.grocery.sainik_grocery.data.model.productlistmodel


import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)