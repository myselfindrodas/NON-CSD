package com.grocery.sainik_grocery.data.model.profilemodel


import com.google.gson.annotations.SerializedName

data class GetProfileRequest(
    @SerializedName("Id")
    val id: String
)