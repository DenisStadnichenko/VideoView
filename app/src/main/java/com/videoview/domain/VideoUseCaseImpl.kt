package com.videoview.domain

import com.videoview.repository.VideoRepository
import javax.inject.Inject

class VideoUseCaseImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : VideoUseCase {

    override fun getVideoByPage() = videoRepository.getVideo()
    override fun getVideo() = videoRepository.getVideoT()
}