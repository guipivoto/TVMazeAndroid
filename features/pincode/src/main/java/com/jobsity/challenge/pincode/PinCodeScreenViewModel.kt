package com.jobsity.challenge.pincode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jobsity.challenge.biometrics.BiometricsApi
import com.jobsity.challenge.settings.SettingsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PinCodeScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val sharedPreferences: SettingsApi,
    private val biometrics : BiometricsApi
) : ViewModel() {

    lateinit var screenEventsHandler: PinCodeScreenEvents
}