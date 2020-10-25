package com.codepb.moviewviewer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ViewingTime(
    val parent: String,
    val times: List<Times>?
)

@Parcelize
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
): Parcelable