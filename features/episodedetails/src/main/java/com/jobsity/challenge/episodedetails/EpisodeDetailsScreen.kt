package com.jobsity.challenge.episodedetails

import com.jobsity.challenge.contract.FeatureContract
import com.jobsity.challenge.contract.FeatureEvents

/**
 * Episode Details Screen API definition
 */
sealed interface EpisodeDetailsScreen : FeatureContract<EpisodeDetailsScreenEvents> {

    companion object {
        const val DESTINATION_NO_ARGS : String = "episode_details_screen"
        const val EPISODE_ID_KEY : String = "episode_id"
    }
}

/**
 * Events produced by the Episode Details Screen
 */
interface EpisodeDetailsScreenEvents : FeatureEvents