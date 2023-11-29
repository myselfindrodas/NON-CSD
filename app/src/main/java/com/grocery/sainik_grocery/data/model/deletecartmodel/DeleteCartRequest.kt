package com.grocery.sainik_grocery.data.model.deletecartmodel


import com.google.gson.annotations.SerializedName

data class DeleteCartRequest(
    @SerializedName("Id")
    val id: String
)