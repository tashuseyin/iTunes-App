package com.tashuseyin.itunesapp.presentation.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tashuseyin.itunesapp.common.NetworkListener
import com.tashuseyin.itunesapp.data.repository.ITunesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val repository: ITunesRepository
) : AndroidViewModel(application) {

    private val _state: MutableStateFlow<DetailState> = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    fun getDetailApi(id: String) {
        viewModelScope.launch {
            if (NetworkListener.hasInternetConnection(getApplication())) {
                try {
                    val response = repository.getDetailApi(id).results!![0]
                    println(response)
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
