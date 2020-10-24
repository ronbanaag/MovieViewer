package com.codepb.moviewviewer.data.model

import com.google.gson.annotations.SerializedName

data class ViewingSchedule (
    val dates: ViewingDate,
    val cinemas: Cinemas,
    val times: ViewingTime
)