package com.videoview.remote.client.video

import com.videoview.data.VideoApi
import com.videoview.remote.responce.VideoResponse
import javax.inject.Inject

class VideoClientImpl @Inject constructor(private val videoApi: VideoApi) : VideoClient {

    override suspend fun requestGetVideo(page: Int): VideoResponse? {
        val response = videoApi.getVideo(page = page)
        return response.body()
    }
}