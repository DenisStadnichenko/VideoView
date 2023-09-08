package com.videoview.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.videoview.remote.client.video.VideoClient
import com.videoview.remote.responce.Video
import com.videoview.remote.source.VideoPagingSource
import kotlinx.coroutines.flow.flow
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

    override fun getVideoT() = flow {
        val res = videoClient.requestGetVideo(1)
        emit(res?.results ?: emptyList<Video>())
    }

}