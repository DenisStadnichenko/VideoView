package com.videoview.data.db.entity

import androidx.room.Entity

import com.squareup.moshi.Json

@Entity(
    tableName = VideoEntity.TABLE_NAME,
    primaryKeys = [VideoEntity.FIELD_ID]
)
data class VideoEntity(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "poster_path")
    val poster: String?,
    @Json(name = "vote_average")
    val vote: Double?,
    @Json(name = "release_date")
    val date: String,
    var isFavorite: Boolean = false
) {

    companion object {
        const val TABLE_NAME = "favorite_video"
        const val FIELD_ID = "id"
    }
}
