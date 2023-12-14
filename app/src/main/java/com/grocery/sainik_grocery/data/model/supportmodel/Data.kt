package com.grocery.sainik_grocery.data.model.supportmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String
)