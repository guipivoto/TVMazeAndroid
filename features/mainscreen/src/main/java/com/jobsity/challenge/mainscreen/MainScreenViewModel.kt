package com.jobsity.challenge.mainscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.challenge.tvshow.TVShowRepository
import com.jobsity.challenge.tvshow.data.TVShowModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainScreenViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val tvShowRepository : TVShowRepository
) : ViewModel() {

    lateinit var screenEventsHandler : MainScreenEvents

    private val _screenState : MutableStateFlow<FetchState> = MutableStateFlow(FetchState.Idle)
    val screenState : StateFlow<FetchState> = _screenState

    init {
        viewModelScope.launch {
            _screenState.value = FetchState.Success(tvShowRepository.getShows())
        }
    }

    fun onItemClicked(tvShow: TVShowModel) {
        screenEventsHandler.onItemSelected(tvShow.id)
    }

    sealed class FetchState {
        object Idle : FetchState()
        data class Success(val tvShows : List<TVShowModel>) : FetchState()
    }
}