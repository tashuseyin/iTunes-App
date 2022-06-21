package com.tashuseyin.itunesapp.presentation.search.state

import com.tashuseyin.itunesapp.domain.model.SearchItem

data class SearchState(
    val isLoading: Boolean = false,
    val searchList: List<SearchItem> = emptyList(),
    val error: String = ""
)
