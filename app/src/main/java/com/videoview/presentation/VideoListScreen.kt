package com.videoview.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.videoview.R
import com.videoview.data.db.entity.VideoEntity

@Composable
fun PagingListScreen(
    viewModel: VideoListViewModel = hiltViewModel(),
    isFavorite: Boolean
) {

    val videosFavorite = viewModel.getVideo(isFavorite).collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(videosFavorite.itemCount) { index ->
            when (val videoDataItem = videosFavorite[index]) {
                is VideoDataItem.Video -> VideoItem(
                    video = videoDataItem.video,
                    isFavorite = isFavorite
                )

                is VideoDataItem.Header -> HeaderItem(title = videoDataItem.title)
                else -> Unit
            }
        }

        when (val state = videosFavorite.loadState.prepend) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> Loading()
            is LoadState.Error -> Error(message = state.error.message ?: "")
        }

        when (val state = videosFavorite.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> Loading()
            is LoadState.Error -> Error(message = state.error.message ?: "")
        }
    }
}

@Composable
fun VideoItem(
    video: VideoEntity,
    isFavorite: Boolean,
    viewModel: VideoListViewModel = hiltViewModel()
) {
    val thumbnail = video.poster
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                val errorImage = painterResource(id = R.drawable.error_video_image)
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500$thumbnail",
                    contentDescription = video.title,
                    modifier = Modifier
                        .width(40.dp)
                        .padding(top = 8.dp)
                        .height(40.dp),
                    error = errorImage
                )
                Text(
                    text = video.vote.toString(),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = video.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = video.overview,
                    fontSize = 12.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = if (isFavorite) "Remove" else "Like",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable {
                                if (isFavorite) viewModel.removeFavorite(video)
                                else viewModel.insertFavorite(video)
                            }
                    )
                    Text(
                        text = "Share",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { share(video.title, context) }
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderItem(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(start = 16.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
}

private fun LazyListScope.Loading() {
    item {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
    }
}

private fun LazyListScope.Error(
    message: String
) {
    item {
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}


fun share(text: String, context: Context) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
    context.startActivity(Intent.createChooser(sharingIntent, "Поделиться с помощью..."))
}
