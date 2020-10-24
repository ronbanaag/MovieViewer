package com.codepb.moviewviewer.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetails (
    @SerializedName("movie_id")
    val movieId: String,
    @SerializedName("advisory_rating")
    val advisoryRating: String,
    @SerializedName("canonical_title")
    val canonicalTitle: String,
    val cast: List<String>?,
    val genre: String,
    @SerializedName("has_schedule")
    val hasSchedule: Int,
    @SerializedName("is_inactive")
    val isInactive: Int,
    @SerializedName("is_showing")
    val isShowing: Int,
    @SerializedName("link_name")
    val linkName: String,
    val poster: String,
    @SerializedName("poster_landscape")
    val posterLandscape: String,
//    val ratings: List<String>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("runtime_mins")
    val runtimeMins: String,
    val synopsis: String,
    val trailer: String,
    @SerializedName("average_rating")
    val averageRating: Int?,
    @SerializedName("total_reviews")
    val totalReviews: String?,
    val variants: List<String>?,
    val theater: String,
    val order: Int,
    @SerializedName("is_featured")
    val isFeatured: Int,
    @SerializedName("watch_list")
    val watchList: Boolean,
    @SerializedName("your_rating")
    val yourRating: Int
)