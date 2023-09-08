package com.videoview.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.videoview.domain.VideoUseCase
import com.videoview.remote.responce.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val videoUseCase: VideoUseCase
) : ViewModel() {

    fun getVideo(): Flow<PagingData<Video>> = videoUseCase.getVideoByPage()
//        .map { pagingData ->
//        pagingData.map { GameModel.GameItem(it) }
//    }.map {
//        it.insertSeparators {before,after->
//            if (after == null) {
//                return@insertSeparators null
//            }
//            if (before == null) {
//                Log.i(TAG, "before is null: ") // never reach here
//                return@insertSeparators GameModel.SeparatorItem("title")
//            }
//            if(condition) {
//                GameModel.SeparatorItem("title")
//            }
//            else null
//        }
}.cachedIn(viewModelScope)

}


sealed class VideoItem {
    class Header(val title: String) : VideoItem()
    class Video(val video: Video) : VideoItem()
}
