package com.grocery.sainik_grocery.data.model.categorymodel


import com.google.gson.annotations.SerializedName

data class CategoryRequest(
    @SerializedName("productMainCategoryId")
    val productMainCategoryId: String
)