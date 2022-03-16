package com.jobsity.challenge.pincode

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
 * Concrete implementation of [PinCodeScreen] screen
 */
internal class PinCodeScreenImpl @Inject constructor() : PinCodeScreen {

    override val plainDestination = "pin_code_screen"

    override val destination = plainDestination

    override fun onCreateNavGraph(
        navGraphBuilder: NavGraphBuilder,
        featureEvents: PinCodeScreenEvents
    ) {
        navGraphBuilder.composable(destination) {
            val viewModel = hiltViewModel<PinCodeScreenViewModel>()
            viewModel.screenEventsHandler = featureEvents
            PinCodeScreenView(viewModel = viewModel)
        }
    }
}

@Module
@InstallIn(ActivityComponent::class)
internal abstract class PinCodeScreenProvider {

    @ActivityScoped
    @Binds
    abstract fun getPinCodeScreenFeature(api: PinCodeScreenImpl): PinCodeScreen
}