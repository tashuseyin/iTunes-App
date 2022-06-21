package com.tashuseyin.itunesapp.data.remote.dto

data class ResultDto(
    val resultCount: Int? = 0,
    val results: List<SearchResultDto>? = listOf()
)