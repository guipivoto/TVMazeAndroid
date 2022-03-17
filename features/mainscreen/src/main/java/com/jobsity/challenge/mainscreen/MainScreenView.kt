package com.jobsity.challenge.mainscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jobsity.challenge.theme.cardElevation
import com.jobsity.challenge.theme.minPadding
import com.jobsity.challenge.tvshow.data.TVShowModel

@Composable
internal fun MainScreenView(viewModel: MainScreenViewModel) {
    when (val screenState = viewModel.screenState.collectAsState().value) {
        is MainScreenViewModel.FetchState.Idle -> Unit
        is MainScreenViewModel.FetchState.Success -> {
            if(screenState.tvShows.isNotEmpty()) {
                DisplayTVShows(screenState.tvShows,
                    { viewModel.onRequestMoreData() }) {
                    viewModel.onItemClicked(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayTVShows(
    showList: List<TVShowModel>,
    requestMoreData: () -> Unit,
    itemClickListener: (TVShowModel) -> Unit
) {
    // Request a new page to Rest API when we are almost reaching the end of the list
    val almostEndingIndex = (showList.size * 0.9).toInt()

    LazyVerticalGrid(
        cells = GridCells.Fixed(integerResource(id = R.integer.main_screen_grid_columns)),
        contentPadding = PaddingValues(minPadding)
    ) {
        items(showList) { show ->
            if (show == showList[almostEndingIndex]) {
                requestMoreData()
            }
            TVShowCard(show.name, show.thumbnailUrl) { itemClickListener(show) }
        }
    }
}

@Composable
fun TVShowCard(showName: String, iconUrl: String?, onClickListener: () -> Unit) {
    Card(
        elevation = cardElevation,
        modifier = Modifier
            .padding(minPadding)
            .clickable { onClickListener() }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val painter = rememberImagePainter(data = iconUrl)

            Image(
                painter = painter,
                contentDescription = showName,
                modifier = Modifier.aspectRatio(3 / 4f),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )

            Spacer(modifier = Modifier.height(minPadding))

            Text(
                text = showName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}