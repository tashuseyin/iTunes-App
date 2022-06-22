package com.tashuseyin.itunesapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.data.remote.ITunesApiService
import com.tashuseyin.itunesapp.data.toDomain
import com.tashuseyin.itunesapp.domain.model.SearchItem

class ITunesPagingSource(
    private val apiService: ITunesApiService,
    private val queries: Map<String, String>
) : PagingSource<Int, SearchItem>() {

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val currentPage = params.key ?: Constant.STARTING_PAGE_INDEX
        val offset = if (params.key != null) ((currentPage - 1) * Constant.PAGE_SIZE) + 1 else Constant.STARTING_PAGE_INDEX
        return try {
            val response =
                apiService.getSearchApi(
                    pageNumber = offset - 1,
                    pageSize = params.loadSize,
                    queries = queries
                )
            val searchList = response.results
            LoadResult.Page(
                data = searchList!!.map { it.toDomain() },
                prevKey = if (currentPage == Constant.STARTING_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (searchList.isEmpty()) null else currentPage + (params.loadSize / Constant.PAGE_SIZE)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}