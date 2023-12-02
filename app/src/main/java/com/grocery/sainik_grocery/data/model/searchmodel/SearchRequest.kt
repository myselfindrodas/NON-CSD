package com.grocery.sainik_grocery.data.model.searchmodel


import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("CategoryId")
    val categoryId: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("pageSize")
    val pageSize: String,
    @SerializedName("skip")
    val skip: String
)