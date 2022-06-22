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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ITunesRepository
) : ViewModel() {

    private val _isSearched: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSearched get() = _isSearched

    var query: String = ""
    var wrapperType: String = Constant.DEFAULT_WRAPPER_TYPE


    private val _searchList: MutableStateFlow<PagingData<SearchItem>> =
        MutableStateFlow(PagingData.empty())
    val searchList = _searchList


    fun getSearchApi() {
        viewModelScope.launch {
            repository.getSearchApi(applyQueries()).cachedIn(viewModelScope).collectLatest {
                _searchList.value = it
            }
        }
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constant.QUERY_WRAPPER_TYPE] = wrapperType
        queries[Constant.QUERY_SEARCH] = query
        return queries
    }
}
