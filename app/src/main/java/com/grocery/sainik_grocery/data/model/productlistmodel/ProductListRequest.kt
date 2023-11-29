package com.grocery.sainik_grocery.data.model.productlistmodel


import com.google.gson.annotations.SerializedName

data class ProductListRequest(
    @SerializedName("pageSize")
    val pageSize: String,
    @SerializedName("skip")
    val skip: String,
    @SerializedName("CategoryId")
    val CategoryId: String

)