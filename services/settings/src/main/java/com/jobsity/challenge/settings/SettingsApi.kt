package com.jobsity.challenge.settings

sealed interface SettingsApi {

    /**
     * Return if user has provisioned the app (if user passed the first screen for the first time)
     * @return True (f user passed the first screen for the first time. False otherwise
     */
    fun isProvisioned(): Boolean

    /**
     *
     */
    fun setIsProvisioned(newValue : Boolean)


    /**
     * Return if user has set an PIN Code to this app
     * @return True if a PIN code was set. False otherwise
     */
    fun isSecured(): Boolean

    /**
     * Set PIN
     * @return True if a PIN code was set. False otherwise
     */
    fun setPinCode(pinCode: String)

    /**
     * Set PIN
     * @return True if a PIN code was set. False otherwise
     */
    fun setUseFingerPrint(useFingerPrint: Boolean)


    /**
     * Compare a PIN Code against the PIN Code defined by the user return if they match
     * @return True if provided PIN code matches the PIN code stored on the shared preference. False otherwise
     */
    fun matches(pinCode: String): Boolean
}