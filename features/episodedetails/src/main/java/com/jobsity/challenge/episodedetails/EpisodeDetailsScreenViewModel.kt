package com.jobsity.challenge.episodedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jobsity.challenge.tvshow.TVShowRepository
import com.jobsity.challenge.tvshow.data.EpisodeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class EpisodeDetailsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val tvShowRepository: TVShowRepository
) : ViewModel() {

    lateinit var screenEventsHandler: EpisodeDetailsScreenEvents

    private val _screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState

    init {
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        data class Fetched(val episode: EpisodeModel) : ScreenState()
    }
}