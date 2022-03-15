package com.jobsity.challenge.episodedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.core.text.HtmlCompat
import coil.compose.rememberImagePainter
import com.jobsity.challenge.resources.R
import com.jobsity.challenge.theme.minPadding
import com.jobsity.challenge.tvshow.data.EpisodeModel

@Composable
internal fun EpisodeDetailsScreenView(viewModel: EpisodeDetailsScreenViewModel) {

    val screenState = viewModel.screenState.collectAsState().value
    if (screenState is EpisodeDetailsScreenViewModel.ScreenState.Fetched) {
        ShowEpisode(screenState.episode)
    }
}

@Composable
private fun ShowEpisode(episode: EpisodeModel) {
    LazyColumn {
        if (episode.imageUrl?.isNotEmpty() == true) {
            item {
                ShowEpisodePicture(episode.imageUrl, episode.name)
            }
        }

        item {
            Text(
                modifier = Modifier.padding(minPadding),
                text = episode.name,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h4
            )
        }

        item {
            SeasonInfo(episode.season, episode.number)
        }

        item {
            Summary(episode.summary)
        }
    }
}

@Composable
private fun Summary(showSummary: String?) {
    showSummary?.let {
        Text(
            text = stringResource(R.string.episode_description),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(minPadding)
        )
        Text(
            text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(minPadding)
        )
    }
}

@Composable
fun SeasonInfo(season: Int, number: Int) {
    Text(
        text = stringResource(R.string.season_episode_number, season, number),
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(minPadding)
    )
}

@Composable
private fun ShowEpisodePicture(pictureUrl: String?, episodeName: String) {
    val painter = rememberImagePainter(pictureUrl)

    Image(
        modifier = Modifier.fillMaxWidth().aspectRatio(16 / 9f),
        painter = painter,
        contentDescription = episodeName,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
    )
}