package com.videoview.remote.client.video

import com.videoview.data.VideoApi
import com.videoview.remote.responce.VideoResponse
import timber.log.Timber
import javax.inject.Inject

class VideoClientImpl @Inject constructor(private val videoApi: VideoApi) : VideoClient {

    override suspend fun requestGetVideo(page: Int): VideoResponse? {
        Timber.d("-> XXX requestGetVideo:${page}")

        val response = videoApi.getVideo(page = page)
        Timber.d("-> XXX requestGetVideo XXX:${response.body()}")
        return response.body()
    }
}