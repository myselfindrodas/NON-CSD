package com.grocery.sainik_grocery.data.model.deletefullcartmodel


import com.google.gson.annotations.SerializedName

data class DeleteCustomerCartRequest(
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("productMainCategoryId")
    val productMainCategoryId: String,
)