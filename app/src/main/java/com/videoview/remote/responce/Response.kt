package com.videoview.remote.responce

data class VideoResponse(
    val results: List<Video>,
    val page: Int
)

data class Video(
    val title: String,
)