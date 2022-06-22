package com.tashuseyin.itunesapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.itunesapp.databinding.ErrorStateBinding

class SearchLoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<SearchLoadingStateAdapter.SearchLoadingViewHolder>() {

    inner class SearchLoadingViewHolder(private val binding: ErrorStateBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                if (loadState is LoadState.Error) {
                    errorText.text = loadState.error.localizedMessage
                }
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                errorText.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(
        holder: SearchLoadingStateAdapter.SearchLoadingViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): SearchLoadingStateAdapter.SearchLoadingViewHolder {
        val binding = ErrorStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchLoadingViewHolder(binding, retry)
    }
}