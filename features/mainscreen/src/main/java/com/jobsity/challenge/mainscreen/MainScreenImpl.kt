package com.jobsity.challenge.mainscreen

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
 * Concrete implementation of [MainScreen] feature (screen)
 */
internal class MainScreenImpl @Inject constructor() : MainScreen {

    override val plainDestination = "main_screen"

    override val destination = plainDestination

    override fun onCreateNavGraph(navGraphBuilder: NavGraphBuilder, featureEvents: MainScreenEvents) {
        navGraphBuilder.composable(destination) {
            val viewModel = hiltViewModel<MainScreenViewModel>()
            viewModel.screenEventsHandler = featureEvents
            MainScreenView(viewModel = viewModel)
        }
    }
}

@Module
@InstallIn(ActivityComponent::class)
internal abstract class MainScreenProvider {

    @ActivityScoped
    @Binds
    abstract fun getMainScreenFeature(api: MainScreenImpl): MainScreen
}