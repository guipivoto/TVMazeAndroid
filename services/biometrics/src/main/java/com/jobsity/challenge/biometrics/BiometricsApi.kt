package com.jobsity.challenge.biometrics

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

sealed interface BiometricsApi {
    /**
     * Return if user has set an PIN Code to this app
     * @return True if a PIN code was set. False otherwise
     */
    fun isBiometricsSupported(): Boolean

    interface BiometricPrompEvents {
        /** Invoked when the user was successfully authenticated */
        fun onSuccess()

        /** Invoked when the user could not be authenticated */
        fun onFailure()

        /** Invoked when the user don't want to use Biometrics for authentication */
        fun onNegative()
    }

    companion object {
        /**
         * Display the Biometric prompt to validate the user
         * @param activity Parent activity
         * @param title Prompt title
         * @param subtitle Prompt subtitle
         * @param negativeButtonText Text to be used on the negative button
         * @param callback Callbacks to be invoked once the dialog is dismissed
         */
        fun showPrompt(
            activity: FragmentActivity,
            title: String,
            subtitle: String,
            negativeButtonText: String,
            callback: BiometricPrompEvents
        ) {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setNegativeButtonText(negativeButtonText)
                .build()

            var biometricPrompt: BiometricPrompt? = null
            biometricPrompt =
                BiometricPrompt(activity, object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                            callback.onNegative()
                        } else {
                            callback.onFailure()
                        }
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        callback.onSuccess()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        callback.onFailure()
                    }
                })

            biometricPrompt.authenticate(promptInfo)
        }
    }
}