package com.videoview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.videoview.domain.VideoUseCase
import com.videoview.remote.responce.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val videoUseCase: VideoUseCase
) : ViewModel() {

    fun getVideo(): Flow<PagingData<Video>> = videoUseCase.getVideoByPage().cachedIn(viewModelScope)

}

