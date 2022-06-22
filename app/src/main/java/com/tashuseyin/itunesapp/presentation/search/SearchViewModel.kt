package com.tashuseyin.itunesapp.presentation.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.domain.model.SearchItem
import com.tashuseyin.itunesapp.domain.repository.ITunesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val repository: ITunesRepository
) : AndroidViewModel(application) {

    private val _isSearched: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSearched get() = _isSearched

    private val _searchList: MutableStateFlow<PagingData<SearchItem>> =
        MutableStateFlow(PagingData.empty())
    val searchList = _searchList


    fun getSearchApi(query: String) {
        viewModelScope.launch {
            repository.getSearchApi(applyQueries(query)).cachedIn(viewModelScope).collectLatest {
                _searchList.value = it
            }
        }
    }

    private fun applyQueries(query: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constant.QUERY_MEDIA_TYPE] = Constant.DEFAULT_MEDIA_TYPE
        queries[Constant.QUERY_SEARCH] = query
        return queries
    }
}