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
                    pageNumber = position,
                    pageSize = Constant.DEFAULT_LIMIT,
                    queries = queries
                )
            val searchList = response.results
            val newKey = (response.resultCount ?: 0) + position
            val nextKey: Int? = if (position == newKey) null else newKey
            LoadResult.Page(
                data = searchList!!.map { it.toDomain() },
                prevKey = null,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}