package com.jobsity.challenge.pincode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.challenge.biometrics.BiometricsApi
import com.jobsity.challenge.settings.SettingsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PinCodeScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val settings: SettingsApi,
    private val biometrics: BiometricsApi
) : ViewModel() {

    lateinit var screenEventsHandler: PinCodeScreenEvents

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    private val _pinState: MutableStateFlow<PinState>
    val pinState: StateFlow<PinState>

    init {
        val isProvisioned = settings.isProvisioned()
        val isProtected = settings.isProtected()
        val usesBiometric = settings.useBiometrics()

        _pinState = MutableStateFlow(PinState(isProvisioned, isProtected))
        pinState = _pinState

        if (isProvisioned && isProtected && usesBiometric && biometrics.isBiometricsSupported()) {
            viewModelScope.launch { _events.send(Event.BiometricAuthentication) }
        }
    }

    /**
     * Handle the scenario where the user don't want to set a PIN code and just move to main screen
     */
    fun onSkipClicked() {
        settings.setIsProvisioned(true)
        screenEventsHandler.onPinCodeCompleted(true)
    }

    /**
     * Handle the result of the prompt where the user can enable biometrics
     * @param enable If Biometrics should be enabled or not
     */
    fun onEnableBiometrics(enable: Boolean) {
        settings.setUseBiometrics(enable)
        screenEventsHandler.onPinCodeCompleted(true)
    }

    /**
     * Handle the biometric authentication result
     * @param result Whether the user was authenticated or if the authentication failed or was canceled
     */
    fun biometricAuthenticationResult(result: BiometricsResult) {
        viewModelScope.launch { _events.send(Event.Idle) }
        if (result == BiometricsResult.SUCCESS) {
            screenEventsHandler.onPinCodeCompleted(true)
        } else if (result == BiometricsResult.NEGATIVE) {
            _pinState.value = PinState(
                settings.isProvisioned(),
                settings.isProtected()
            )
        }
    }

    /**
     * A pin was inserted. If the device was not provisioned, it means the user is setting a PIN Code.
     * @param pin PIN Code inserted by the user
     */
    fun onPinInserted(pin: String) {
        if (settings.isProvisioned()) {
            if (settings.isProtected() && settings.matches(pin)) {
                screenEventsHandler.onPinCodeCompleted(true)
            }
        } else {
            settings.setIsProvisioned(true)
            settings.setPinCode(pin)
            if (biometrics.isBiometricsSupported()) {
                viewModelScope.launch { _events.send(Event.PromptBiometric) }
            } else {
                screenEventsHandler.onPinCodeCompleted(true)
            }
        }
    }

    data class PinState(
        /**
         *  Whether the app is provisioned or not. In other words, tells if user passed the first screen at least one time
         */
        val isProvisioned: Boolean,

        /**
         * Whether the app is protected by a PIN or not
         */
        val isProtected: Boolean
    )

    /**
     * Possible return types from the Biometric Prompt
     */
    enum class BiometricsResult {
        SUCCESS, FAILURE, NEGATIVE
    }

    /**
     * Objects that can be use to send single time events to View
     */
    sealed class Event {

        /** Idle state. Nothing to do */
        object Idle : Event()

        /** Request to display an alert dialog so the user can enable Biometrics if he want to */
        object PromptBiometric : Event()

        /** Send this event so the view displays the BiometricPrompt dialog */
        object BiometricAuthentication : Event()
    }
}