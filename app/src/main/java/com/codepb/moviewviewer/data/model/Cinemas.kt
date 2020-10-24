package com.codepb.moviewviewer.data.model

import com.google.gson.annotations.SerializedName

data class Cinemas (
    val id: String,
    @SerializedName("cinema_id")
    val cinemaId: String,
    val label: String?
)