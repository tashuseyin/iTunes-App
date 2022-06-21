package com.tashuseyin.itunesapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.itunesapp.databinding.SearchItemRowBinding
import com.tashuseyin.itunesapp.domain.model.SearchItem

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {
    var searchItemList = emptyList<SearchItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            SearchItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searchItemList[position])
    }

    override fun getItemCount() = searchItemList.size

    fun setData(newSearchItemList: List<SearchItem>) {
        this.searchItemList = newSearchItemList
    }
}