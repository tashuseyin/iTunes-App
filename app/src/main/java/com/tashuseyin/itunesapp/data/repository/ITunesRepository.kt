package com.tashuseyin.itunesapp.data.repository

import androidx.paging.PagingData
import com.tashuseyin.itunesapp.data.remote.dto.BaseResultDto
import com.tashuseyin.itunesapp.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface ITunesRepository {
    fun getSearchApi(queries: Map<String, String>): Flow<PagingData<SearchItem>>
    suspend fun getDetailApi(id: String): BaseResultDto
}