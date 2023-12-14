package com.grocery.sainik_grocery.data.model.faqmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("answer")
    val answer: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("topic")
    val topic: String,
    var isExpanded: Boolean=false
)