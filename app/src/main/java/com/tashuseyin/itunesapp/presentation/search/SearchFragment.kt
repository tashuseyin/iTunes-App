package com.tashuseyin.itunesapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.tashuseyin.itunesapp.R
import com.tashuseyin.itunesapp.databinding.FragmentSearchBinding
import com.tashuseyin.itunesapp.presentation.binding_adapter.BindingFragment
import com.tashuseyin.itunesapp.presentation.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(), SearchView.OnQueryTextListener {
    private val searchViewModel: SearchViewModel by viewModels()
    private val args: SearchFragmentArgs by navArgs()
    private var querySave: String = ""
    private val adapter = SearchAdapter()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(this)
        searchViewModel.saveQuery(querySave)
        setListener()
        backBottomSheetRequestApi()
    }

    private fun setListener() {
        binding.apply {
            filterButton.setOnClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_bottomSheetFragment)
            }
        }
    }

    private fun backBottomSheetRequestApi() {
        lifecycleScope.launch {
            searchViewModel.readQuery.observe(viewLifecycleOwner) { query ->
                if (query.isNotBlank() && args.backBottomSheet) {
                    searchViewModel.getSearchApi(query)
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            querySave = query
            searchViewModel.getSearchApi(query)
            observeUI()
        }
        return true
    }

    override fun onQueryTextChange(p0: String?) = true


    private fun observeUI() {
        lifecycleScope.launch {
            searchViewModel.state.collect { state ->
                binding.apply {
                    progressBar.isVisible = state.isLoading
                    if (state.error.isNotBlank()) {
                        errorText.text = state.error
                    }
                    if (state.searchList.isNotEmpty()) {
                        adapter.setData(state.searchList)
                        binding.recyclerView.adapter = adapter
                    }
                }
            }
        }
    }
}