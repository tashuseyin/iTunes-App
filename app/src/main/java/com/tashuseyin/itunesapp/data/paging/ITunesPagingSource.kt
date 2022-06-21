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
        val currentPage = params.key ?: Constant.STARTING_PAGE_INDEX
        return try {
            val response =
                apiService.getSearchApi(queries, offset = currentPage ,limit = params.loadSize )
            val searchList = response.results
            LoadResult.Page(
                data = searchList!!.map { it.toDomain() },
                prevKey = if (currentPage == Constant.STARTING_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (searchList.isEmpty()) null else currentPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}