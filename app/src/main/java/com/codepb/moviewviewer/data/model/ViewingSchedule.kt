package com.codepb.moviewviewer.data.model

import com.google.gson.annotations.SerializedName

data class ViewingSchedule (
    val dates: List<ViewingDate>,
    val cinemas: List<ViewingCinemas>,
    val times: List<ViewingTime>
)