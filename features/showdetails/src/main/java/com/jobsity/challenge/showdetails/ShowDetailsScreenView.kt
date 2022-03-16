package com.jobsity.challenge.showdetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.rememberImagePainter
import com.jobsity.challenge.resources.R
import com.jobsity.challenge.theme.*
import com.jobsity.challenge.tvshow.data.EpisodeModel
import com.jobsity.challenge.tvshow.data.Schedule
import com.jobsity.challenge.tvshow.data.ShowStatus
import com.jobsity.challenge.tvshow.data.TVShowModel

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
    tvShowDetail: TVShowModel,
    episodes: List<List<EpisodeModel>>?,
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
                iconUrl = tvShowDetail.imageUrl,
                rating = tvShowDetail.rating
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
    status: ShowStatus,
    genres: List<String>?,
    premiered: String?,
    ended: String?,
    schedule: Schedule?,
    iconUrl: String?,
    rating: Float
) {
    Row {
        val painter = rememberImagePainter(data = iconUrl,
            builder = {
                placeholder(R.color.blue_500)
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

            val statusColor = if (status == ShowStatus.ENDED) {
                Red_800
            } else {
                Green_800
            }
            Text(
                modifier = Modifier
                    .background(statusColor, RoundedCornerShape(4.dp))
                    .padding(2.dp),
                color = Grey_50,
                text = status.toString(),
                style = MaterialTheme.typography.caption
            )

            Spacer(modifier = Modifier.height(minPadding))

            if(genres != null) {
                Genres(genres)
            }

            if(rating > 0) {
                Rating(rating)
            }

            Text(
                text = stringResource(R.string.show_premiered, premiered ?: Unit),
                style = MaterialTheme.typography.body2
            )

            if(status == ShowStatus.ENDED) {
                Text(
                    text = stringResource(R.string.show_ended, ended ?: Unit),
                    style = MaterialTheme.typography.body2
                )
            }

            schedule?.let { schedule ->
                Text(
                    text = stringResource(R.string.show_time, schedule.time),
                    style = MaterialTheme.typography.body2
                )

                var daysOfWeek = ""
                schedule.days.forEach { daysOfWeek += "$it " }
                Text(
                    text = stringResource(R.string.show_days, daysOfWeek),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
private fun Rating(rating: Float) {
    var fontColor = Grey_50
    val backgroundColor = when(rating) {
        in 0f..4.5f -> Red_800
        in 4.5f..7.0f -> {
            fontColor = Grey_900
            Yellow_600
        }
        else -> Green_800
    }
    Row {
        Text(
        text = stringResource(R.string.show_rating),
        style = MaterialTheme.typography.body2
        )
        Text(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(4.dp))
                .padding(2.dp),
            maxLines = 1,
            color = fontColor,
            text = rating.toString(),
            style = MaterialTheme.typography.caption
        )
    }

}

@Composable
private fun Genres(genres: List<String>) {
    Row {
        genres.forEach {
            Box(modifier = Modifier.padding(2.dp)) {
                Text(
                    modifier = Modifier
                        .background(Yellow_600, RoundedCornerShape(4.dp))
                        .padding(2.dp),
                    maxLines = 1,
                    color = Grey_900,
                    text = it,
                    style = MaterialTheme.typography.caption
                )
            }
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
            text = it,
            style = MaterialTheme.typography.body2,
            modifier = modifier
        )
    }
}

@Composable
private fun SeasonHeader(seasonNumber: Int) {
    Box(
        modifier = Modifier
            .height(42.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(minPadding),
            text = stringResource(id = R.string.season_header, seasonNumber),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1
        )
    }
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
private fun Episode(episode: EpisodeModel, onEpisodeClicked: (Long) -> Unit) {
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
        status = ShowStatus.ENDED,
        genres = listOf("Drama", "Science-Fiction", "Thriller"),
        premiered = "2013-06-24",
        ended = "2015-09-10",
        schedule = Schedule("22:00", listOf("Thursday")),
        iconUrl = "iconURL",
        rating = 6.8f
    )
}