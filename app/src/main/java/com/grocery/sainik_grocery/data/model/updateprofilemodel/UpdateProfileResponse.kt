package com.grocery.sainik_grocery.data.model.updateprofilemodel


import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)