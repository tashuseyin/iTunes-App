package com.tashuseyin.itunesapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.viewbinding.ViewBinding
import com.tashuseyin.itunesapp.R
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.common.extension.hideKeyboard
import com.tashuseyin.itunesapp.databinding.FragmentSearchBinding
import com.tashuseyin.itunesapp.domain.model.SharedModelDetail
import com.tashuseyin.itunesapp.presentation.binding_adapter.BindingFragment
import com.tashuseyin.itunesapp.presentation.search.adapter.SearchAdapter
import com.tashuseyin.itunesapp.presentation.search.adapter.SearchLoadingStateAdapter
import com.tashuseyin.itunesapp.presentation.search.adapter.WrapperTypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(), SearchView.OnQueryTextListener {
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val adapter = SearchAdapter()
    private val wrapperTypeAdapter = WrapperTypeAdapter()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUI()
        setListener()
        initWrapperTypeAdapter()
        filterSearchRequestApi()
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(this)
    }

    private fun setListener() {
        adapter.onItemClickListener = { searchItem ->
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                    SharedModelDetail(
                        trackId = searchItem.trackId.toString(),
                        collectionId = searchItem.collectionId.toString(),
                        wrapperType = searchViewModel.wrapperType
                    )
                )
            )
        }
    }

    private fun filterSearchRequestApi() {
        wrapperTypeAdapter.onItemClickListener = { wrapperType ->
            when (wrapperType) {
                "Application" -> {
                    searchViewModel.wrapperType = Constant.PARAMS_APPLICATION
                }
                "Music" -> {
                    searchViewModel.wrapperType = Constant.PARAMS_MUSIC
                }
                else -> {
                    searchViewModel.wrapperType = wrapperType.lowercase()
                }
            }
            if (searchViewModel.query.isNotBlank()) {
                requestSearchApi()
            }
        }
    }

    private fun initWrapperTypeAdapter() {
        val mediaTypeList = ArrayList<String>()
        mediaTypeList.add(getString(R.string.movie))
        mediaTypeList.add(getString(R.string.book))
        mediaTypeList.add(getString(R.string.music))
        mediaTypeList.add(getString(R.string.application))

        wrapperTypeAdapter.setData(mediaTypeList)
        binding.recyclerViewFilter.adapter = wrapperTypeAdapter
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
                errorText.text = getString(R.string.not_found, searchViewModel.wrapperType)
                emptyAnimation.isVisible = true
            } else {
                errorText.isVisible = false
                emptyAnimation.isVisible = false
            }
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            view?.let { hideKeyboard(view = it) }
            searchViewModel.query = query
            requestSearchApi()
            observeUI()
        }
        return true
    }

    override fun onQueryTextChange(p0: String?) = true


    private fun requestSearchApi() {
        lifecycleScope.launch {
            searchViewModel.getSearchApi()
            searchViewModel.searchList.collect {
                adapter.submitData(it)
                binding.recyclerView.adapter = adapter
            }
        }
    }
}