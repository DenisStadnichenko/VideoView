package com.videoview.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.videoview.remote.responce.Video
import timber.log.Timber


@Composable
fun PagingListScreen(
    viewModel: VideoListViewModel = hiltViewModel()
) {

    val videos = viewModel.getVideo().collectAsLazyPagingItems()
    Timber.d("-> loadState:${videos.loadState}")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Scroll for more recipes!",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
        when (val state = videos.loadState.prepend) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }
        when (val state = videos.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }

        itemsIndexed(
            videos.itemSnapshotList.items
        ) { i, x ->
            val item = videos[i]
            Timber.d("-> XXXX :$item")
            Text(
                text = item?.title ?: "xxx",
                style = MaterialTheme.typography.bodySmall
            )
        }

        when (val state = videos.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }
    }
}

@Composable
fun VideoItem(
    video: Video,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.Top) {
        Spacer(modifier = Modifier.width(15.dp))
        // val thumbnail = video.volumeInfo?.imageLinks?.thumbnail?.replaceFirst("http:", "https:")
        Column(
            Modifier
                .fillMaxHeight()
                .weight(0.2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 30.dp))
//            AsyncImage(
//                model = thumbnail,
//                contentDescription = book.volumeInfo?.title,
//                modifier = Modifier
//            )
        }
        Column(
            Modifier
                .fillMaxHeight()
                .weight(0.8f)
                .padding(30.dp)
        ) {
            Text(
                text = video.title,
                //style = Typography.h3
            )
//            Text(
//                text = HtmlCompat.fromHtml(
//                    book.searchInfo?.textSnippet.orEmpty(),
//                    HtmlCompat.FROM_HTML_MODE_COMPACT
//                ).toAnnotatedString(),
//                textAlign = TextAlign.Justify
//            )
        }
    }
}


@Composable
fun CallOnDispose(action: () -> Unit) {
    DisposableEffect(Unit) {
        onDispose {
            action.invoke()
        }
    }
}

@Composable
fun CallOnLaunch(action: () -> Unit) {
    LaunchedEffect(Unit) {
        action.invoke()
    }
}

private fun LazyListScope.Loading() {
    item {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
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