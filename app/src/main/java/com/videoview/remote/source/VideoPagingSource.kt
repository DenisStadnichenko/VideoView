package com.videoview.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.videoview.remote.client.video.VideoClient
import com.videoview.remote.responce.Video

class VideoPagingSource(
    private val newsApiService: VideoClient
) : PagingSource<Int, Video>() {

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        return try {
            val page = params.key ?: 1
            val response = newsApiService.requestGetVideo(page = page)

            LoadResult.Page(
                data = response?.results ?: emptyList(),
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}