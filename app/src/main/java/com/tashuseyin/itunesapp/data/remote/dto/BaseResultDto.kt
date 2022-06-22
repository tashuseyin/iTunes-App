package com.tashuseyin.itunesapp.data.remote.dto

data class BaseResultDto(
    val resultCount: Int? = 0,
    val results: List<BaseResultItemDto>? = listOf()
)