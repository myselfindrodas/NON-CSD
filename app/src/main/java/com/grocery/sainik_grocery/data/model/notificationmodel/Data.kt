package com.grocery.sainik_grocery.data.model.notificationmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("customerId")
    val customerId: Any?,
    @SerializedName("dailyReminders")
    val dailyReminders: Any?,
    @SerializedName("dayOfWeek")
    val dayOfWeek: Any?,
    @SerializedName("endDate")
    val endDate: Any?,
    @SerializedName("frequency")
    val frequency: Int,
    @SerializedName("halfYearlyReminders")
    val halfYearlyReminders: Any?,
    @SerializedName("id")
    val id: String,
    @SerializedName("isEmailNotification")
    val isEmailNotification: Boolean,
    @SerializedName("isRepeated")
    val isRepeated: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("quarterlyReminders")
    val quarterlyReminders: Any?,
    @SerializedName("reminderUsers")
    val reminderUsers: Any?,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("subject")
    val subject: String
):Serializable