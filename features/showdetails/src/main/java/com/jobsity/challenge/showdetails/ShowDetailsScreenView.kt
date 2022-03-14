package com.jobsity.challenge.showdetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.rememberImagePainter
import com.jobsity.challenge.resources.R
import com.jobsity.challenge.theme.minPadding
import com.jobsity.challenge.tvshow.data.Episode
import com.jobsity.challenge.tvshow.data.Schedule
import com.jobsity.challenge.tvshow.data.TVShowDetail

@Composable
internal fun ShowDetailsScreenView(viewModel: ShowDetailsScreenViewModel) {
    when (val screenState = viewModel.screenState.collectAsState().value) {
        is ShowDetailsScreenViewModel.ScreenState.Loading -> Unit
        is ShowDetailsScreenViewModel.ScreenState.Fetched -> ShowTVShow(
            screenState.tvShowDetail,
            screenState.episodes
        ) {
            viewModel.onEpisodeClicked(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowTVShow(
    tvShowDetail: TVShowDetail,
    episodes: List<List<Episode>>?,
    onEpisodeClicked: (Long) -> Unit
) {
    LazyColumn {
        item {
            Header(
                showName = tvShowDetail.name,
                status = tvShowDetail.status,
                genres = tvShowDetail.genres,
                premiered = tvShowDetail.premiered,
                ended = tvShowDetail.ended,
                schedule = tvShowDetail.schedule,
                iconUrl = tvShowDetail.images?.original
            )
        }

        item {
            Summary(showSummary = tvShowDetail.summary, Modifier.padding(minPadding))
        }


        if (episodes?.isNotEmpty() == true) {
            item {
                EpisodesHeader()
            }

            episodes.forEachIndexed { index, season ->
                stickyHeader {
                    SeasonHeader(seasonNumber = index + 1)
                }

                items(season.size) {
                    Episode(season[it], onEpisodeClicked)
                }
            }
        }
    }
}

@Composable
private fun Header(
    showName: String,
    status: String?,
    genres: List<String>?,
    premiered: String?,
    ended: String?,
    schedule: Schedule?,
    iconUrl: String?
) {
    Row {
        val painter = rememberImagePainter(data = iconUrl,
            builder = {
                placeholder(android.R.color.black)
            }
        )

        Image(
            painter = painter,
            contentDescription = showName,
            modifier = Modifier
                .height(200.dp)
                .aspectRatio(3 / 4f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        )

        Spacer(modifier = Modifier.width(minPadding))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = showName,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(minPadding))

            Text(
                text = stringResource(R.string.show_status, status ?: ""),
                style = MaterialTheme.typography.body2
            )
            Text(text = genres?.toString() ?: "", style = MaterialTheme.typography.body2)
            Text(
                text = stringResource(R.string.show_premiered, premiered ?: Unit),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = stringResource(R.string.show_ended, ended ?: Unit),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = stringResource(R.string.show_time, schedule?.time ?: Unit),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = stringResource(R.string.show_days, schedule?.days ?: Unit),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
private fun Summary(showSummary: String?, modifier: Modifier = Modifier) {
    showSummary?.let {
        Text(
            text = stringResource(R.string.show_description),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h6,
            modifier = modifier
        )
        Text(
            text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
            style = MaterialTheme.typography.body2,
            modifier = modifier
        )
    }
}

@Composable
private fun SeasonHeader(seasonNumber: Int) {
    Text(
        modifier = Modifier.fillMaxWidth().padding(minPadding),
        text = stringResource(id = R.string.season_header, seasonNumber),
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun EpisodesHeader() {
    Text(
        modifier = Modifier.padding(minPadding),
        text = stringResource(id = R.string.show_episodes),
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h6
    )
}

@Composable
private fun Episode(episode: Episode, onEpisodeClicked: (Long) -> Unit) {
    Box(
        modifier = Modifier
            .height(36.dp)
            .fillMaxWidth()
            .padding(horizontal = minPadding)
            .clickable { onEpisodeClicked(episode.id) },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = episode.name,
            style = MaterialTheme.typography.body2
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Test() {
    Header(
        showName = "Under the Dome",
        status = "Ended",
        genres = listOf("Drama", "Science-Fiction", "Thriller"),
        premiered = "2013-06-24",
        ended = "2015-09-10",
        schedule = Schedule("22:00", listOf("Thursday")),
        iconUrl = "iconURL"
    )
}