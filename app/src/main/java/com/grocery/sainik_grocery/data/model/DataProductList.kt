package com.example.sainikgrocerycustomer.data.model

import com.google.gson.annotations.SerializedName

data class DataProductList(

    val sellingPrice: Double,
    val discount: Double, // 60
    val days_left: Int, // 60
    val image: Int, // null
    val name: String, // salt
    val price: Double, // 25
    val units_of_measurement_types: String, // Kg
    val id: Int // 1

)
