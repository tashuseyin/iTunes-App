package com.tashuseyin.itunesapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tashuseyin.itunesapp.databinding.SearchItemRowBinding
import com.tashuseyin.itunesapp.domain.model.SearchItem

class SearchAdapter : PagingDataAdapter<SearchItem, SearchViewHolder>(ITunesDifferCallback()) {
    var onItemClickListener: ((SearchItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            SearchItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position)!!, onItemClickListener)
    }
}

class ITunesDifferCallback : DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem) =
        oldItem.trackId == newItem.trackId

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem) =
        oldItem == newItem

}