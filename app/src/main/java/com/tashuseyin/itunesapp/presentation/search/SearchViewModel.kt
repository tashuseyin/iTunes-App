package com.tashuseyin.itunesapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.data.data_store.DataStoreRepository
import com.tashuseyin.itunesapp.data.toDomain
import com.tashuseyin.itunesapp.domain.repository.ITunesRepository
import com.tashuseyin.itunesapp.presentation.search.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val repository: ITunesRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state

    fun getSearchApi(query: String) {
        viewModelScope.launch {
            _state.value = SearchState(isLoading = true)
            try {
                val response =
                    repository.getSearchApi(applyQueries(query)).results?.map { it.toDomain() }
                _state.value = SearchState(searchList = response ?: emptyList())
            } catch (e: Exception) {
                _state.value = SearchState(error = e.localizedMessage ?: "An unexpected error.")
            }
        }
    }


    private fun applyQueries(query: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readSelectMediaType.collect {
                queries[Constant.QUERY_MEDIA_TYPE] = it.mediaType
            }
        }
        queries[Constant.QUERY_SEARCH] = query
        return queries
    }

    val readSelectMediaType = dataStoreRepository.readMediaType

    fun saveSelectMediaType(mediaType: String, mediaTypeId: Int) {
        viewModelScope.launch {
            dataStoreRepository.saveMediaType(mediaType, mediaTypeId)
        }
    }

    fun saveQuery(query: String) = viewModelScope.launch {
        dataStoreRepository.saveQuery(query)
    }

    val readQuery = dataStoreRepository.readQuery.asLiveData()
}