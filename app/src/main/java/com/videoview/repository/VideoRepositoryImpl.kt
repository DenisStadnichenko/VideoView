package com.videoview.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.videoview.remote.client.video.VideoClient
import com.videoview.remote.source.VideoPagingSource
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoClient: VideoClient
) : VideoRepository {

    override fun getVideo() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            VideoPagingSource(videoClient)
        },
        initialKey = 1

    ).flow

}