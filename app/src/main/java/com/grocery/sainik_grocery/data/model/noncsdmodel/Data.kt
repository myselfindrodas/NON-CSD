package com.grocery.sainik_grocery.data.model.noncsdmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("counterName")
    val counterName: String,
    @SerializedName("id")
    val id: String,
    var isSelected: Boolean = false

)