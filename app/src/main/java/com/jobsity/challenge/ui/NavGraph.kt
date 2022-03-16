package com.jobsity.challenge.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jobsity.challenge.contract.FeatureContract
import com.jobsity.challenge.episodedetails.EpisodeDetailsScreen
import com.jobsity.challenge.episodedetails.EpisodeDetailsScreenEvents
import com.jobsity.challenge.mainscreen.MainScreen
import com.jobsity.challenge.mainscreen.MainScreenEvents
import com.jobsity.challenge.pincode.PinCodeScreen
import com.jobsity.challenge.pincode.PinCodeScreenEvents
import com.jobsity.challenge.showdetails.ShowDetailsScreen
import com.jobsity.challenge.showdetails.ShowDetailsScreenEvents

@Composable
fun NavGraph(
    startDestination: FeatureContract<*>,
    mainScreen: MainScreen,
    showDetailsScreen: ShowDetailsScreen,
    episodeDetailsScreen: EpisodeDetailsScreen,
    pinCodeScreen: PinCodeScreen
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination.destination) {
        mainScreen.onCreateNavGraph(this, object : MainScreenEvents {
            override fun onItemSelected(showId: Long) {
                navController.navigate(showDetailsScreen.plainDestination + "/" + showId)
            }
        })

        showDetailsScreen.onCreateNavGraph(this, object : ShowDetailsScreenEvents {
            override fun onEpisodeSelected(episodeId: Long) {
                navController.navigate(episodeDetailsScreen.plainDestination + "/" + episodeId)
            }
        })

        episodeDetailsScreen.onCreateNavGraph(this, object : EpisodeDetailsScreenEvents {})

        pinCodeScreen.onCreateNavGraph(this, object : PinCodeScreenEvents {
            override fun onPinCodeCompleted(authorized : Boolean) {
                navController.popBackStack()
                navController.navigate(mainScreen.plainDestination)
            }
        })
    }
}