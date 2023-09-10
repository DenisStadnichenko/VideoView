package com.videoview.domain

import androidx.paging.PagingData
import com.videoview.data.db.entity.VideoEntity
import kotlinx.coroutines.flow.Flow

interface VideoUseCase {

    fun getVideoByPage(): Flow<PagingData<VideoEntity>>
    fun getFavoriteVideoEntity(): Flow<PagingData<VideoEntity>>
    suspend fun insertFavorite(video: VideoEntity)
    suspend fun removeFavorite(video: VideoEntity)
}