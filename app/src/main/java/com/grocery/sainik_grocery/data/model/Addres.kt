package com.example.sainikgrocerycustomer.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Addres(
    @SerializedName("apartment_name")
    val apartmentName: String, // sd.
    @SerializedName("area")
    val area: String, // asewq.
    @SerializedName("city")
    val city: String, // wdw.
    @SerializedName("created_at")
    val createdAt: String, // 2023-04-06T11:42:31.000000Z
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("house_no")
    val houseNo: String, // 34.
    @SerializedName("id")
    val id: Int, // 3
    @SerializedName("is_primary")
    var isPrimary: Int, // 0
    @SerializedName("pincode")
    val pincode: String, // 232123.
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("street_details")
    val streetDetails: String, // sdas.
    @SerializedName("type")
    val type: String, // Others
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-04-06T11:42:31.000000Z
    @SerializedName("user_id")
    val userId: Int // 2
) : Serializable