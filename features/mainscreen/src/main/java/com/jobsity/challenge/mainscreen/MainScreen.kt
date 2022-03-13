package com.jobsity.challenge.mainscreen

import com.jobsity.challenge.contract.FeatureContract
import com.jobsity.challenge.contract.FeatureEvents

/**
 * Main Screen API definition
 */
sealed interface MainScreen : FeatureContract<MainScreenEvents>

/**
 * Events produced by the Main Screen
 */
interface MainScreenEvents : FeatureEvents {

    /**
     * Method invoked when an TV Show is selected
     * @param showId ID of the show selected
     */
    fun onItemSelected(showId : Long)
}