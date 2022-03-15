package com.jobsity.challenge.episodedetails

import com.jobsity.challenge.contract.FeatureContract
import com.jobsity.challenge.contract.FeatureEvents

/**
 * Episode Details Screen API definition
 */
sealed interface EpisodeDetailsScreen : FeatureContract<EpisodeDetailsScreenEvents> {

    companion object {

        /** Key to be add arguments when navigating to this screen */
        internal const val EPISODE_ID_ARGS: String = "episode_id"
    }
}

/**
 * Events produced by the Episode Details Screen
 */
interface EpisodeDetailsScreenEvents : FeatureEvents