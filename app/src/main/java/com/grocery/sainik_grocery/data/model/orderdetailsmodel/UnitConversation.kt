package com.grocery.sainik_grocery.data.model.orderdetailsmodel


import com.google.gson.annotations.SerializedName

data class UnitConversation(
    @SerializedName("baseUnitName")
    val baseUnitName: Any?,
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("operator")
    val `operator`: Any?,
    @SerializedName("parentId")
    val parentId: Any?,
    @SerializedName("value")
    val value: Any?
)