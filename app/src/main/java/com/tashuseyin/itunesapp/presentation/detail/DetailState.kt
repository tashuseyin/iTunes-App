package com.tashuseyin.itunesapp.presentation.detail

import com.tashuseyin.itunesapp.data.remote.dto.BaseResultItemDto

data class DetailState(
    val isLoading: Boolean = true,
    val detailItem: BaseResultItemDto? = null,
    val error: String = ""
)
