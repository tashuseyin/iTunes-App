package com.tashuseyin.itunesapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.tashuseyin.itunesapp.R
import com.tashuseyin.itunesapp.databinding.FragmentSearchBinding
import com.tashuseyin.itunesapp.presentation.binding_adapter.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(), SearchView.OnQueryTextListener {

    private val searchViewModel: SearchViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(this)
        setListener()
    }

    private fun setListener() {
        binding.apply {
            filterButton.setOnClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_bottomSheetFragment)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            requestApi(query)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?) = true

    private fun requestApi(query: String) {
        lifecycleScope.launch {
            searchViewModel.searchMovies(query)
        }
    }
}