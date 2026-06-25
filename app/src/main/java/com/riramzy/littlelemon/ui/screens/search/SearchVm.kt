package com.riramzy.littlelemon.ui.screens.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.repos.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchVm @Inject constructor(
    private val searchRepo: SearchRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val KEY_SEARCH_STATE = "search_state"
    }

    private val _searchState = savedStateHandle.getStateFlow(
        KEY_SEARCH_STATE,
        SearchState(
            query = savedStateHandle.get<String>("query") ?: "",
            category = savedStateHandle.get<String>("category")
        )
    )

    val searchQuery: StateFlow<String> = _searchState.map { it.query }
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val categoryFilter: StateFlow<String?> = _searchState.map { it.category }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchedItems = _searchState
        .debounce { state ->
            if (state.isQueryTriggered && state.query.isNotEmpty()) {
                300.milliseconds
            } else {
                0.milliseconds
            }
        }
        .flatMapLatest { state ->
            searchRepo.getFilteredMenuItems(state.query, state.category)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_0000),
            emptyList()
        )

    fun onSearchQueryChange(query: String) {
        val currentState = _searchState.value

        savedStateHandle[KEY_SEARCH_STATE] = currentState.copy(
            query = query,
            category = if (query.isBlank()) null else currentState.category,
            isQueryTriggered = true
        )
    }

    fun searchByCategory(category: String?) {
        savedStateHandle[KEY_SEARCH_STATE] = SearchState(
            query = "",
            category = category,
            isQueryTriggered = false
        )
    }

    fun clearFilters() {
        savedStateHandle[KEY_SEARCH_STATE] = SearchState()
    }
}
