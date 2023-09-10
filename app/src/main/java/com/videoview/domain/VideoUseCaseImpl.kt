package com.videoview.domain

import com.videoview.data.db.entity.VideoEntity
import com.videoview.repository.VideoRepository
import javax.inject.Inject

class VideoUseCaseImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : VideoUseCase {

    override fun getVideoByPage() = videoRepository.getVideo()
    override fun getFavoriteVideoEntity() = videoRepository.getFavoriteVideo()
    override suspend fun insertFavorite(video: VideoEntity) = videoRepository.insertFavorite(video)
    override suspend fun removeFavorite(video: VideoEntity) = videoRepository.removeFavorite(video)
}