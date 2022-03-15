package com.jobsity.challenge.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jobsity.challenge.episodedetails.EpisodeDetailsScreen
import com.jobsity.challenge.episodedetails.EpisodeDetailsScreenEvents
import com.jobsity.challenge.mainscreen.MainScreen
import com.jobsity.challenge.mainscreen.MainScreenEvents
import com.jobsity.challenge.showdetails.ShowDetailsScreen
import com.jobsity.challenge.showdetails.ShowDetailsScreenEvents

@Composable
fun NavGraph(
    mainScreen: MainScreen,
    showDetailsScreen: ShowDetailsScreen,
    episodeDetailsScreen: EpisodeDetailsScreen
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = mainScreen.plainDestination) {
        mainScreen.onCreateNavGraph(this,  object : MainScreenEvents {
            override fun onItemSelected(showId: Long) {
                navController.navigate(showDetailsScreen.plainDestination + "/" + showId)
            }
        })

        showDetailsScreen.onCreateNavGraph(this, object : ShowDetailsScreenEvents{
            override fun onEpisodeSelected(episodeId: Long) {
                navController.navigate(episodeDetailsScreen.plainDestination + "/" + episodeId)
            }
        })

        episodeDetailsScreen.onCreateNavGraph(this, object : EpisodeDetailsScreenEvents {})
    }
}