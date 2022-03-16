package com.jobsity.challenge.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.jobsity.challenge.episodedetails.EpisodeDetailsScreen
import com.jobsity.challenge.mainscreen.MainScreen
import com.jobsity.challenge.pincode.PinCodeScreen
import com.jobsity.challenge.settings.SettingsApi
import com.jobsity.challenge.showdetails.ShowDetailsScreen
import com.jobsity.challenge.theme.TVTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var mainScreen: MainScreen

    @Inject
    lateinit var showDetailsScreen: ShowDetailsScreen

    @Inject
    lateinit var episodeDetailsScreen: EpisodeDetailsScreen

    @Inject
    lateinit var pinCodeScreen: PinCodeScreen

    @Inject
    lateinit var sharedPref: SettingsApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BuildNavGraph()
            }
        }
    }
    }

    @Composable
    fun BuildNavGraph() {
        val startingScreen = if (sharedPref.isProvisioned() && !sharedPref.isProtected()) {
            mainScreen
        } else {
            pinCodeScreen
        }
        NavGraph(
            startDestination = startingScreen,
            mainScreen = mainScreen,
            showDetailsScreen = showDetailsScreen,
            episodeDetailsScreen = episodeDetailsScreen,
            pinCodeScreen = pinCodeScreen
        )
    }
}