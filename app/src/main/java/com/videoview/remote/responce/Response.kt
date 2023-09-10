package com.videoview.remote.responce

import com.squareup.moshi.Json

data class VideoResponse(
    val results: List<Video>,
    val page: Int
)

data class Video(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "poster_path")
    val poster: String?,
    @Json(name = "vote_average")
    val vote: Double,
    @Json(name = "release_date")
    val date: String
)