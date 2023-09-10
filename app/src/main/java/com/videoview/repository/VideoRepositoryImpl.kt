package com.videoview.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.videoview.data.db.dao.FavoriteVideoDao
import com.videoview.data.db.entity.VideoEntity
import com.videoview.data.mapper.EntityVideoMapper
import com.videoview.remote.client.video.VideoClient
import com.videoview.remote.source.VideoPagingSource
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoClient: VideoClient,
    private val videoDao: FavoriteVideoDao,
    private val entityVideoMapper: EntityVideoMapper
) : VideoRepository {

    override fun getVideo() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            VideoPagingSource(videoClient, entityVideoMapper)
        }

    ).flow

    override fun getFavoriteVideo() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            videoDao.getFavorite()
        }

    ).flow

    override suspend fun insertFavorite(video: VideoEntity) {
        videoDao.insert(video.apply { isFavorite = true })
    }

    override suspend fun removeFavorite(video: VideoEntity) {
        videoDao.remove(video.apply { isFavorite = true })
    }
}