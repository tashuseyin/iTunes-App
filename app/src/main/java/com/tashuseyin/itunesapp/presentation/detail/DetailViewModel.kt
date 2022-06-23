package com.tashuseyin.itunesapp.presentation.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.common.NetworkListener
import com.tashuseyin.itunesapp.data.toDomain
import com.tashuseyin.itunesapp.domain.model.SharedModelDetail
import com.tashuseyin.itunesapp.domain.repository.ITunesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val repository: ITunesRepository,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val _state: MutableStateFlow<DetailState> = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    init {
        savedStateHandle.get<SharedModelDetail>(Constant.PARAM_SHARED_MODEL)?.let { it ->
            if (it.wrapperType == Constant.PARAMS_AUDIOBOOK) {
                it.collectionId?.let { collectionId ->
                    getDetailApi(collectionId)
                }
            } else {
                it.trackId?.let { trackId ->
                    getDetailApi(trackId)
                }
            }
        }
    }

    private fun getDetailApi(id: String) {
        viewModelScope.launch {
            if (NetworkListener.hasInternetConnection(getApplication())) {
                _state.value = DetailState(isLoading = true)
                try {
                    val response = repository.getDetailApi(id).results!![0].toDomain()
                    _state.value = DetailState(detailItem = response, isScrollView = true)
                } catch (e: Exception) {
                    _state.value =
                        DetailState(error = e.localizedMessage ?: "An unexpected error occurred")
                }
            } else {
                _state.value =
                    DetailState(error = "Couldn't reach server. Check your internet connection.")
            }
        }
    }
}
