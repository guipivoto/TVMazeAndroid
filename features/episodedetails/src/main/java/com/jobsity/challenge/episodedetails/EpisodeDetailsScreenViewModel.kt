package com.jobsity.challenge.episodedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.challenge.tvshow.TVShowRepository
import com.jobsity.challenge.tvshow.data.EpisodeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
        savedStateHandle.get<String>(EpisodeDetailsScreen.EPISODE_ID_KEY)?.toLong()?.also {
            viewModelScope.launch {
                _screenState.value = ScreenState.Fetched(tvShowRepository.getEpisode(it))
            }
        }
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        data class Fetched(val episode: EpisodeModel) : ScreenState()
    }
}