package com.tashuseyin.itunesapp.data.repository

import com.tashuseyin.itunesapp.data.remote.ITunesApiService
import com.tashuseyin.itunesapp.data.remote.dto.ResultDto
import com.tashuseyin.itunesapp.domain.repository.ITunesRepository
import javax.inject.Inject

class ITunesRepositoryImpl @Inject constructor(
    private val apiService: ITunesApiService
) : ITunesRepository {

    override suspend fun getSearchApi(queries: Map<String, String>): ResultDto {
        return apiService.getSearchApi(queries)
    }
}