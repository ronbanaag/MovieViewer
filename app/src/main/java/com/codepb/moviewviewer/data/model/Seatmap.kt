package com.codepb.moviewviewer.data.model

import com.google.gson.annotations.SerializedName

data class Seatmap (
    val seatmap: List<List<String>?>?,
    val available: AvailableSeats?
)

data class AvailableSeats(
    val seats: List<String>,
    @SerializedName("seat_count")
    val seatCount: Int
)