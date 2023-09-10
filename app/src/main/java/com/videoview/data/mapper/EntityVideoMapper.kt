package com.videoview.data.mapper

import com.videoview.data.db.entity.VideoEntity
import com.videoview.remote.responce.Video

interface EntityVideoMapper {
    suspend fun mapVideoResponseToEntity(video: Video, isFavorite: Boolean = false): VideoEntity
}