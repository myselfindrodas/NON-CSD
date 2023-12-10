package com.grocery.sainik_grocery.data.model.productmaincategorymodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("categoryImageUrl")
    val categoryImageUrl: String
)