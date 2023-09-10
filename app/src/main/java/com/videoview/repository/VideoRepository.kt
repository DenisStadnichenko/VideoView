package com.videoview.repository

import androidx.paging.PagingData
import com.videoview.data.db.entity.VideoEntity
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    fun getVideo(): Flow<PagingData<VideoEntity>>
    fun getFavoriteVideo(): Flow<PagingData<VideoEntity>>
    suspend fun insertFavorite(video: VideoEntity)
    suspend fun removeFavorite(video: VideoEntity)

}