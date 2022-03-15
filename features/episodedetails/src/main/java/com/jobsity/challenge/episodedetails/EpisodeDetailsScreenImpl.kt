package com.jobsity.challenge.episodedetails

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

    override val plainDestination: String = "episode_details_screen"

    override val destination = plainDestination + "/{" + EpisodeDetailsScreen.EPISODE_ID_ARGS + "}"

    override fun onCreateNavGraph(
        navGraphBuilder: NavGraphBuilder,
        featureEvents: EpisodeDetailsScreenEvents
    ) {
        navGraphBuilder.composable(
            destination,
            listOf(navArgument(EpisodeDetailsScreen.EPISODE_ID_ARGS) { type = NavType.LongType })
        ) {
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