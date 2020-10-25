package com.codepb.moviewviewer.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewingDate(
    val id: String,
    val label: String,
    val date: String?
): Parcelable