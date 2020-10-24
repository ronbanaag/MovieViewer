package com.codepb.moviewviewer.data.model

import com.google.gson.annotations.SerializedName

data class ViewingTime(
    val parent: String,
    val times: Times
)

data class Times (
    val id: String,
    val label: String?,
    @SerializedName("schedule_id")
    val scheduleId: String,
    @SerializedName("popcorn_price")
    val popcornPrice: String?,
    @SerializedName("popcorn_label")
    val popcornLabel: String?,
    @SerializedName("seating_type")
    val seatingType: String,
    val price: String,
    val variant: String
)