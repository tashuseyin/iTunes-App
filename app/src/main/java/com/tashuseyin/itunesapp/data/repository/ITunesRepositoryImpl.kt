package com.tashuseyin.itunesapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.data.paging.ITunesPagingSource
import com.tashuseyin.itunesapp.data.remote.ITunesApiService
import com.tashuseyin.itunesapp.data.remote.dto.BaseResultDto
import com.tashuseyin.itunesapp.domain.model.SearchItem
import com.tashuseyin.itunesapp.domain.repository.ITunesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ITunesRepositoryImpl @Inject constructor(
    private val apiService: ITunesApiService
) : ITunesRepository {

    override fun getSearchApi(queries: Map<String, String>): Flow<PagingData<SearchItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constant.STARTING_PAGE_INDEX,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ITunesPagingSource(apiService, queries) }
        ).flow
    }

    override suspend fun getDetailApi(id: String): BaseResultDto {
        return apiService.getDetailApi(id)
    }
}