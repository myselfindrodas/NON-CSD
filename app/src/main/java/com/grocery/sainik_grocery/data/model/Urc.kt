package com.example.sainikgrocerycustomer.data.model

import com.google.gson.annotations.SerializedName

data class Urc(
    @SerializedName("distance")
    val distance: Double, // 9.493529796600341796875e-5
    @SerializedName("id")
    val id: Int, // 3
    @SerializedName("name")
    val name: String,

    var isSelected: Boolean = false
)
