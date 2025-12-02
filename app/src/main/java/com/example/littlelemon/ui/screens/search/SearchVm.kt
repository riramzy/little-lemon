package com.example.littlelemon.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.local.menu.LocalMenuItem
import com.example.littlelemon.data.repos.SearchRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SearchVm(private val repo: SearchRepo) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // Holds the category passed from the home screen
    private val _categoryFilter = MutableStateFlow<String?>(null)
    val categoryFilter = _categoryFilter.asStateFlow()

    // This flow combines the query and category to produce the final list of items
    val searchedItems = combine(_searchQuery.debounce(300), _categoryFilter) { query, category ->
        Pair(query, category)
    }.flatMapLatest { (query, category) ->
        val results: Flow<List<LocalMenuItem>> = if (query.isBlank()) {
            // If query is blank, show all items in the category, or all items if no category is set
            if (category.isNullOrBlank()) {
                repo.getAllMenuItems()
            } else {
                repo.searchByCategory(category)
            }
        } else {
            // If there is a query, search by name
            val searchResults = repo.searchByName(query)

            // If a category is also set, filter the search results by that category
            if (category.isNullOrBlank()) {
                searchResults
            } else {
                searchResults.map { list -> list.filter { it.category == category } }
            }
        }
        results
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun onSearchQueryChange(query: String) {
        _searchQuery.update { query }
        // When the user starts typing, clear the category filter
        if (query.isNotBlank()) {
            _categoryFilter.update { null }
        }
    }

    /**
     * Sets the category to filter by. This is typically called once when the
     * screen is first displayed after navigating from the home screen.
     */
    fun searchByCategory(category: String) {
        _categoryFilter.update { category }
        // When a category is selected, clear the search query
        _searchQuery.update { "" }
    }
}
