package com.tashuseyin.itunesapp.domain.repository

import androidx.paging.PagingData
import com.tashuseyin.itunesapp.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface ITunesRepository {
    fun getSearchApi(queries: Map<String, String>): Flow<PagingData<SearchItem>>
}