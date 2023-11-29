package com.grocery.sainik_grocery.data.model.postordermodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.StringTokenizer

data class UnitConversation(
    @SerializedName("baseUnitName")
    val baseUnitName: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("operator")
    val `operator`: String?,
    @SerializedName("parentId")
    val parentId: String?,
    @SerializedName("value")
    val value: String?
):Serializable