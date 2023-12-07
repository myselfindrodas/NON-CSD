package com.grocery.sainik_grocery.data.model.getcartlistmodel


import com.google.gson.annotations.SerializedName

data class ProductCategory(
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("createdByUser")
    val createdByUser: Any?,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("deletedBy")
    val deletedBy: Any?,
    @SerializedName("deletedDate")
    val deletedDate: Any?,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("isDeleted")
    val isDeleted: Boolean,
    @SerializedName("modifiedBy")
    val modifiedBy: String,
    @SerializedName("modifiedDate")
    val modifiedDate: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("objectState")
    val objectState: Int,
    @SerializedName("parentCategory")
    val parentCategory: Any?,
    @SerializedName("parentId")
    val parentId: Any?,
    @SerializedName("productCategoryUrl")
    val productCategoryUrl: Any?
)