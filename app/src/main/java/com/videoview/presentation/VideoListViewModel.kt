package com.videoview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.videoview.data.db.entity.VideoEntity
import com.videoview.domain.VideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val videoUseCase: VideoUseCase
) : ViewModel() {

    fun getVideo(isFavorite: Boolean = false): Flow<PagingData<VideoDataItem>> {
        val video = if (!isFavorite) videoUseCase.getVideoByPage()
        else videoUseCase.getFavoriteVideoEntity()
        return video
            .mapToVideoDataItem()
            .insertDateSeparator()
    }

    private fun Flow<PagingData<VideoEntity>>.mapToVideoDataItem() = this.map { pagingData ->
        pagingData.map { VideoDataItem.Video(it) }
    }

    private fun Flow<PagingData<VideoDataItem.Video>>.insertDateSeparator() = this.map {
        it.insertSeparators { before, after ->

            val afterData = formatDate(after?.video?.date)
            val beforeData = formatDate(before?.video?.date)

            val isFirstItem = before == null

            if (isFirstItem || beforeData != afterData) {
                VideoDataItem.Header(afterData)
            } else {
                null
            }
        }
    }

    private fun formatDate(videDate: String?): String {
        if (videDate.isNullOrEmpty()) return ""
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputFormat = SimpleDateFormat("MMM yyyy", Locale.US)

        return try {
            val date = inputFormat.parse(videDate)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            videDate
        }
    }

    fun insertFavorite(video: VideoEntity) {
        viewModelScope.launch {
            videoUseCase.insertFavorite(video)
        }
    }

    fun removeFavorite(video: VideoEntity) {
        viewModelScope.launch {
            videoUseCase.removeFavorite(video)
        }
    }
}

sealed class VideoDataItem {
    class Header(val title: String) : VideoDataItem()
    class Video(val video: VideoEntity) : VideoDataItem()
}