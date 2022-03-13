package com.jobsity.challenge.showdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.challenge.tvshow.TVShowRepository
import com.jobsity.challenge.tvshow.data.TVShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ShowDetailsScreenViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val tvShowRepository : TVShowRepository
) : ViewModel() {

    lateinit var screenEventsHandler : ShowDetailsScreenEvents

    private val _screenState : MutableStateFlow<FetchState> = MutableStateFlow(FetchState.Idle)
    val screenState : StateFlow<FetchState> = _screenState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = FetchState.Success(tvShowRepository.getShows())
        }
    }

    sealed class FetchState {
        object Idle : FetchState()
        data class Success(val tvShows : List<TVShow>) : FetchState()
    }
}