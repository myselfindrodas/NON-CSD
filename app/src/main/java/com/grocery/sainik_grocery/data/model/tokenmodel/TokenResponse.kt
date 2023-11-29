package com.grocery.sainik_grocery.data.model.tokenmodel


import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("bearerToken")
    val bearerToken: String,
    @SerializedName("claims")
    val claims: List<Claim>,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("isAuthenticated")
    val isAuthenticated: Boolean,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("profilePhoto")
    val profilePhoto: String,
    @SerializedName("userName")
    val userName: String
)