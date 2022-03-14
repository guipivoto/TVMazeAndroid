package com.jobsity.challenge.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jobsity.challenge.mainscreen.MainScreen
import com.jobsity.challenge.mainscreen.MainScreenEvents
import com.jobsity.challenge.showdetails.ShowDetailsScreen
import com.jobsity.challenge.showdetails.ShowDetailsScreenEvents

@Composable
fun NavGraph(mainScreen: MainScreen, showDetailsScreen: ShowDetailsScreen) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = mainScreen.destination) {
        mainScreen.onCreateNavGraph(this,  object : MainScreenEvents {
            override fun onItemSelected(showId: Long) {
                navController.navigate(ShowDetailsScreen.DESTINATION_NO_ARGS + "/" + showId)
            }
        })

        showDetailsScreen.onCreateNavGraph(this, object : ShowDetailsScreenEvents{
            override fun onEpisodeSelected(episodeId: Long) {
                println("Episode $episodeId")
            }
        })
    }
}