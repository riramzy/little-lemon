package com.riramzy.littlelemon.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.repos.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchVm @Inject constructor(
    private val searchRepo: SearchRepo
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _categoryFilter = MutableStateFlow<String?>(null)
    val categoryFilter = _categoryFilter.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchedItems = combine(
        _searchQuery.debounce(300),
        _categoryFilter
    ) { query, category ->
        query to category
    }.flatMapLatest { (query, category) ->

        searchRepo.getAllMenuItems().map { items ->
            items
                .let { list ->
                    if (category.isNullOrBlank()) list
                    else list.filter { it.category.equals(category, true) }
                }
                .let { list ->
                    if (query.isBlank()) list
                    else list.filter {
                        it.title.contains(query, ignoreCase = true)
                    }
                }
        }

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isNotBlank()) {
            _categoryFilter.update { null }
        }
    }

    fun searchByCategory(category: String?) {
        _categoryFilter.value = category
    }

    fun clearFilters() {
        _searchQuery.value = ""
        _categoryFilter.value = null
    }
}
