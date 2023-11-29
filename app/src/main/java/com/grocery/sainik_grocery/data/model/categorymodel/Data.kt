package com.grocery.sainik_grocery.data.model.categorymodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("productCategoryUrl")
    val productCategoryUrl: String,
    @SerializedName("parentId")
    val parentId: Any?
)