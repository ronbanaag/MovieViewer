package com.codepb.moviewviewer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Cinemas(
    val id: String,
    @SerializedName("cinema_id")
    val cinemaId: String,
    val label: String?
): Parcelable

@Parcelize
data class ViewingCinemas(
    val parent: String,
    @SerializedName("cinemas")
    val cinemaList: List<Cinemas>?
): Parcelable