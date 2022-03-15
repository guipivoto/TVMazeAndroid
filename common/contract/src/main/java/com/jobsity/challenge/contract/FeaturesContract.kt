package com.jobsity.challenge.contract

import androidx.navigation.NavGraphBuilder

/**
 * Interface that should be implemented by any screen (feature) in order to be added to Navigation
 * Graph.
 */
interface FeatureContract<in T : FeatureEvents> {

    /**
     * Plain Navigation Destination (destination without any argument)
     */
    val plainDestination: String

    /**
     * Navigation Destination (destination with any additional argument)
     */
    val destination: String

    /**
     * Method that will be invoked during Nav Graph creation so each feature can insert itself
     * into the nav graph
     */
    fun onCreateNavGraph(navGraphBuilder: NavGraphBuilder, featureEvents: T)
}

/**
 * This is a generic interface used by a feature to produce external events such as Navigate to
 * other screen etc. Since each screen is hidden from other ones, it can't direcly navigate to
 * other screens
 */
interface FeatureEvents