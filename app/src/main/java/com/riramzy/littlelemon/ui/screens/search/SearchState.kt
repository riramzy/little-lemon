package com.riramzy.littlelemon.ui.screens.search

import java.io.Serializable

data class SearchState(
    val query: String = "",
    val category: String? = null,
    val isQueryTriggered: Boolean = false
) : Serializable