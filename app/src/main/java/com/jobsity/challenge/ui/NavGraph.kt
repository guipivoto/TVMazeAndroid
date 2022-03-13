package com.jobsity.challenge.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jobsity.challenge.mainscreen.MainScreen
import com.jobsity.challenge.mainscreen.MainScreenEvents

@Composable
fun NavGraph(mainScreen: MainScreen) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = mainScreen.destination) {
        mainScreen.onCreateNavGraph(this,  object : MainScreenEvents {
            override fun onItemSelected(showId: Long) {
                //TODO Navigate to other screen
                // navController.navigate()
            }
        })
    }
}