package com.tashuseyin.itunesapp.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.domain.model.SearchItem
import com.tashuseyin.itunesapp.domain.repository.ITunesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ITunesRepository
) : ViewModel() {

    private var job: Job? = null
    private val _searchList = MutableLiveData<PagingData<SearchItem>>(PagingData.empty())
    val searchList = _searchList

    fun searchMovies(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getSearchApi(queries = applyQueries(query)).cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest {
                    _searchList.value = it
                }
        }
    }

    fun applyQueries(query: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[Constant.QUERY_MEDIA_TYPE] = Constant.DEFAULT_MEDIA_TYPE
        queries[Constant.QUERY_SEARCH] = query
        return queries
    }

}