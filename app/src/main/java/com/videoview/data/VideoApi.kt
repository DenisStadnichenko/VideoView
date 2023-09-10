package com.videoview.data

import com.videoview.remote.responce.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface VideoApi {

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNDBhZmU0YjU4MmI4OGYxZGVmMWVmNGVjZmY0Y2NjYSIsInN1YiI6IjY0ZjgxZWM0OGUyMGM1MGNkNTBjM2MzNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.RJ-fNa1JteSXOqAud8Q_Bk6bdnyevXLmDRPOBTBOv8E"
    )
    @GET("movie")
    suspend fun getVideo(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "primary_release_date.desc"
    ): Response<VideoResponse>
}