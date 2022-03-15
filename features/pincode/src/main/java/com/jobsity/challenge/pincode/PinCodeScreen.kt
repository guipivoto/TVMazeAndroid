package com.jobsity.challenge.pincode

import com.jobsity.challenge.contract.FeatureContract
import com.jobsity.challenge.contract.FeatureEvents

/**
 * Show Details Screen API definition
 */
sealed interface PinCodeScreen : FeatureContract<PinCodeScreenEvents>

/**
 * Events produced by the Show Details Screen
 */
interface PinCodeScreenEvents : FeatureEvents {


}