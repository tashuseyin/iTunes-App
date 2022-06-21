package com.tashuseyin.itunesapp.domain.repository

import com.tashuseyin.itunesapp.data.remote.dto.ResultDto

interface ITunesRepository {
    suspend fun getSearchApi(queries: Map<String, String>): ResultDto
}