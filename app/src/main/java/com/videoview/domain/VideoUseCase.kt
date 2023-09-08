package com.videoview.domain

import androidx.paging.PagingData
import com.videoview.remote.responce.Video
import kotlinx.coroutines.flow.Flow

interface VideoUseCase {

    fun getVideoByPage(): Flow<PagingData<Video>>
    fun getVideo(): Flow<List<Video>>
}