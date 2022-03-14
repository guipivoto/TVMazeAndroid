package com.jobsity.challenge.showdetails

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
 * Concrete implementation of [ShowDetailsScreen] screen
 */
internal class ShowDetailsScreenImpl @Inject constructor() : ShowDetailsScreen {

    override val destination =
        ShowDetailsScreen.DESTINATION_NO_ARGS + "/{" + ShowDetailsScreen.SHOW_ID_KEY + "}"

    override fun onCreateNavGraph(
        navGraphBuilder: NavGraphBuilder,
        featureEvents: ShowDetailsScreenEvents
    ) {
        navGraphBuilder.composable(destination) {
            val viewModel = hiltViewModel<ShowDetailsScreenViewModel>()
            viewModel.screenEventsHandler = featureEvents
            ShowDetailsScreenView(viewModel = viewModel)
        }
    }
}

@Module
@InstallIn(ActivityComponent::class)
internal abstract class ShowDetailsScreenProvider {

    @ActivityScoped
    @Binds
    abstract fun getShowDetailsScreenFeature(api: ShowDetailsScreenImpl): ShowDetailsScreen
}