package com.tashuseyin.itunesapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.viewbinding.ViewBinding
import com.tashuseyin.itunesapp.R
import com.tashuseyin.itunesapp.common.extension.hideKeyboard
import com.tashuseyin.itunesapp.databinding.FragmentSearchBinding
import com.tashuseyin.itunesapp.presentation.binding_adapter.BindingFragment
import com.tashuseyin.itunesapp.presentation.search.adapter.SearchAdapter
import com.tashuseyin.itunesapp.presentation.search.adapter.SearchLoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(), SearchView.OnQueryTextListener {
    private val searchViewModel: SearchViewModel by viewModels()
    private val adapter = SearchAdapter()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel.isSearched.value = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkIsSearched()
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(this)
    }

    private fun observeUI() {
        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = SearchLoadingStateAdapter { adapter.retry() }
        )
        lifecycleScope.launch {
            adapter.addLoadStateListener { loadState ->
                binding.apply {
                    recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                    progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    handleError(loadState)
                }
            }
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        binding.apply {
            if (loadState.source.refresh is LoadState.Error) {
                errorText.isVisible = loadState.source.refresh is LoadState.Error
                errorText.text =
                    (loadState.source.refresh as LoadState.Error).error.localizedMessage
            } else if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                errorText.isVisible = true
                errorText.text = getString(R.string.not_found)
                emptyAnimation.isVisible = true
            } else {
                errorText.isVisible = false
                emptyAnimation.isVisible = false
            }
        }
    }

    private fun checkIsSearched() {
        binding.apply {
            searchViewModel.isSearched.observe(viewLifecycleOwner) {
                binding.apply {
                    errorText.isVisible = it
                    errorText.text = "Please enter the movie you want to search."
                    searchAnimation.isVisible = it
                }
            }
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            view?.let { hideKeyboard(view = it) }
            searchViewModel.isSearched.value = false
            requestSearch(query)
            observeUI()
        }
        return true
    }

    override fun onQueryTextChange(p0: String?) = true


    private fun requestSearch(query: String) {
        lifecycleScope.launch {
            searchViewModel.getSearchApi(query)
            searchViewModel.searchList.collect {
                adapter.submitData(it)
                binding.recyclerView.adapter = adapter
            }
        }
    }
}