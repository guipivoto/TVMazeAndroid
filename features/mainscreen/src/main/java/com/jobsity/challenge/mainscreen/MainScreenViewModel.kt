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

    /** Show list which will be filled as the user scroll for more items */
    private val currentShowList : MutableList<TVShowModel> = mutableListOf()
    /** Current page requested to the repository */
    private var currentPage = 0

    private val _screenState : MutableStateFlow<FetchState> = MutableStateFlow(FetchState.Idle)
    val screenState : StateFlow<FetchState> = _screenState

    init {
        viewModelScope.launch {
            currentShowList.addAll(tvShowRepository.getShows(currentPage))
            _screenState.value = FetchState.Success(currentShowList, currentPage)
        }
    }

    fun onItemClicked(tvShow: TVShowModel) {
        screenEventsHandler.onItemSelected(tvShow.id)
    }

    fun onRequestMoreData() {
        viewModelScope.launch {
            currentShowList.addAll(tvShowRepository.getShows(++currentPage))
            _screenState.value = FetchState.Success(currentShowList, currentPage)
        }
    }

    sealed class FetchState {
        object Idle : FetchState()
        data class Success(val tvShows : List<TVShowModel>, val page : Int) : FetchState()
    }
}