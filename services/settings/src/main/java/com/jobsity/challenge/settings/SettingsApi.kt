package com.jobsity.challenge.settings

sealed interface SettingsApi {

    /**
     * Return if user has provisioned the app (if user passed the first screen for the first time)
     * @return True (f user passed the first screen for the first time. False otherwise
     */
    fun isProvisioned(): Boolean

    /**
     * Set if the app was provisioned
     * @param newValue True if the app was provisioned. False otherwise.
     */
    fun setIsProvisioned(newValue : Boolean)

    /**
     * Return if user set a PIN Code to this app
     * @return True if a PIN code was set. False otherwise
     */
    fun isProtected(): Boolean

    /**
     * Store the PIN code
     * @param pinCode 4 characters large string that will be used as PIN Code
     */
    fun setPinCode(pinCode: String)

    /**
     * Return if user opted to use Biometrics as authentication method.
     * @return True if Biometrics should be used as as authentication method
     */
    fun useBiometrics(): Boolean

    /**
     * Set if biometrics should be used as as authentication method
     * @param useBiometrics Whether Biometrics should be used to authenticate the user or not
     */
    fun setUseBiometrics(useBiometrics: Boolean)


    /**
     * Compare a PIN Code against the PIN Code defined by the user return if they match
     * @return True if provided PIN code matches the PIN code stored on the shared preference. False otherwise
     */
    fun matches(pinCode: String): Boolean
}