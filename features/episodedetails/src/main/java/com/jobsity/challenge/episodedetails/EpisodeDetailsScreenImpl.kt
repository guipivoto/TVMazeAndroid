package com.jobsity.challenge.episodedetails

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Concrete implementation of [EpisodeDetailsScreen] screen
 */
internal class EpisodeDetailsScreenImpl @Inject constructor() : EpisodeDetailsScreen {

    override val destination =
        EpisodeDetailsScreen.DESTINATION_NO_ARGS + "/{" + EpisodeDetailsScreen.EPISODE_ID_KEY + "}"

    override fun onCreateNavGraph(
        navGraphBuilder: NavGraphBuilder,
        featureEvents: EpisodeDetailsScreenEvents
    ) {
        navGraphBuilder.composable(destination) {
            val viewModel = hiltViewModel<EpisodeDetailsScreenViewModel>()
            viewModel.screenEventsHandler = featureEvents
            EpisodeDetailsScreenView(viewModel = viewModel)
        }
    }
}

@Module
@InstallIn(ActivityComponent::class)
internal abstract class EpisodeDetailsScreenProvider {

    @ActivityScoped
    @Binds
    abstract fun getEpisodeDetailsScreenFeature(api: EpisodeDetailsScreenImpl): EpisodeDetailsScreen
}