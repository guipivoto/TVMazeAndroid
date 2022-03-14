package com.jobsity.challenge.showdetails

import com.jobsity.challenge.contract.FeatureContract
import com.jobsity.challenge.contract.FeatureEvents

/**
 * Show Details Screen API definition
 */
sealed interface ShowDetailsScreen : FeatureContract<ShowDetailsScreenEvents> {

    companion object {
        const val DESTINATION_NO_ARGS : String = "show_details_screen"
        const val SHOW_ID_KEY : String = "show_id"
    }
}

/**
 * Events produced by the Show Details Screen
 */
interface ShowDetailsScreenEvents : FeatureEvents {

    /**
     * Event triggered when user clicks on a Episode
     * @param episodeId ID of the episode clicked by the user
     */
    fun onEpisodeSelected(episodeId : Long)
}