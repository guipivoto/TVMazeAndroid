package com.jobsity.challenge.showdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.challenge.tvshow.TVShowRepository
import com.jobsity.challenge.tvshow.data.EpisodeModel
import com.jobsity.challenge.tvshow.data.TVShowModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ShowDetailsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val tvShowRepository: TVShowRepository
) : ViewModel() {

    lateinit var screenEventsHandler: ShowDetailsScreenEvents

    private val _screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState

    init {
        viewModelScope.launch {
            savedStateHandle.get<String?>(ShowDetailsScreen.SHOW_ID_KEY)?.toInt()?.also {
                val tvShowDetail = tvShowRepository.getShow(it)
                _screenState.value = ScreenState.Fetched(tvShowDetail)
                val episodes = tvShowRepository.getEpisodes(it)
                _screenState.value = ScreenState.Fetched(tvShowDetail, episodes)
            }
        }
    }

    fun onEpisodeClicked(episodeId : Long) {
        screenEventsHandler.onEpisodeSelected(episodeId = episodeId)
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        data class Fetched(val tvShowDetail: TVShowModel, val episodes : List<List<EpisodeModel>>? = null) : ScreenState()
    }
}