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
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val position = params.key ?: Constant.STARTING_PAGE_INDEX
        return try {
            val response =
                apiService.getSearchApi(
                    pageNumber = position - 1,
                    pageSize = Constant.DEFAULT_LIMIT,
                    queries = queries
                )
            val searchList = response.results
            LoadResult.Page(
                data = searchList!!.map { it.toDomain() },
                prevKey = if (position == Constant.STARTING_PAGE_INDEX) null else position - Constant.DEFAULT_LIMIT,
                nextKey = if (searchList.isEmpty()) null else (position) + Constant.DEFAULT_LIMIT
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}