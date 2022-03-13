package com.jobsity.challenge.showdetails

import com.jobsity.challenge.contract.FeatureContract
import com.jobsity.challenge.contract.FeatureEvents

/**
 * Show Details Screen API definition
 */
sealed interface ShowDetailsScreen : FeatureContract<ShowDetailsScreenEvents>

/**
 * Events produced by the Show Details Screen
 */
interface ShowDetailsScreenEvents : FeatureEvents