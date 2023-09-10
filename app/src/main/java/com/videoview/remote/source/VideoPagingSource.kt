package com.videoview.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.videoview.data.db.entity.VideoEntity
import com.videoview.data.mapper.EntityVideoMapper
import com.videoview.remote.client.video.VideoClient

class VideoPagingSource(
    private val newsApiService: VideoClient,
    private val mapper: EntityVideoMapper
) : PagingSource<Int, VideoEntity>() {

    override fun getRefreshKey(state: PagingState<Int, VideoEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoEntity> {
        return try {
            val page = params.key ?: 1
            val response = newsApiService.requestGetVideo(page = page)
            val video =
                response?.results?.map { mapper.mapVideoResponseToEntity(it) } ?: emptyList()

            LoadResult.Page(
                data = video,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (video.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}