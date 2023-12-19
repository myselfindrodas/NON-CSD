package com.grocery.sainik_grocery.data.model.appversionmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("appType")
    val appType: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("version")
    val version: String
)