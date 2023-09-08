package com.videoview.remote.client.video

import com.videoview.remote.responce.VideoResponse

interface VideoClient {

    suspend fun requestGetVideo(page: Int): VideoResponse?

}