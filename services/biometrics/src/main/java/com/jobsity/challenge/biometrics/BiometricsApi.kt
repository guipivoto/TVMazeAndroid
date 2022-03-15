package com.jobsity.challenge.biometrics

sealed interface BiometricsApi {
    /**
     * Return if user has set an PIN Code to this app
     * @return True if a PIN code was set. False otherwise
     */
    fun isFingerprintSupported(): Boolean
}