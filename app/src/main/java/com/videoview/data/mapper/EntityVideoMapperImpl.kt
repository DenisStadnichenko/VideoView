package com.videoview.data.mapper

import com.videoview.data.db.entity.VideoEntity
import com.videoview.remote.responce.Video
import javax.inject.Inject

class EntityVideoMapperImpl @Inject constructor() : EntityVideoMapper {
    override suspend fun mapVideoResponseToEntity(video: Video, isFavorite: Boolean): VideoEntity {
        return VideoEntity(
            id = video.id,
            title = video.title,
            overview = video.overview,
            poster = video.poster,
            vote = video.vote,
            date = video.date,
            isFavorite = isFavorite
        )
    }
}