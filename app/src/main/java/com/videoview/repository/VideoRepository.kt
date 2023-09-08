package com.videoview.repository

import androidx.paging.PagingData
import com.videoview.remote.responce.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {


    fun getVideo(): Flow<PagingData<Video>>
    fun getVideoT(): Flow<List<Video>>


}