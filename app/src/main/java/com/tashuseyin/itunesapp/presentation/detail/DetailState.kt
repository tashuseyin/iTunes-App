package com.tashuseyin.itunesapp.presentation.detail

import com.tashuseyin.itunesapp.domain.model.SearchItem

data class DetailState(
    val isLoading: Boolean = false,
    val detailItem: SearchItem? = null,
    val isScrollView : Boolean = false,
    val error: String = ""
)
