package com.tashuseyin.itunesapp.presentation.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.itunesapp.common.extension.loadImageView
import com.tashuseyin.itunesapp.common.extension.placeholderProgressBar
import com.tashuseyin.itunesapp.databinding.SearchItemRowBinding
import com.tashuseyin.itunesapp.domain.model.SearchItem

class SearchViewHolder(private val binding: SearchItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(searchItem: SearchItem) {
        binding.apply {
            searchItemImage.loadImageView(
                searchItem.artworkUrl100,
                placeholderProgressBar(searchItemImage.context)
            )
            searchItemTitle.text = searchItem.trackName
            searchItemPrice.text = when {
                searchItem.collectionPrice.toString()
                    .isNotEmpty() -> searchItem.collectionPrice.toString()
                searchItem.trackPrice.toString().isNotEmpty() -> searchItem.trackPrice.toString()
                searchItem.trackRentalPrice.toString()
                    .isNotEmpty() -> searchItem.trackRentalPrice.toString()
                searchItem.trackHdRentalPrice.toString()
                    .isNotEmpty() -> searchItem.trackHdRentalPrice.toString()
                searchItem.collectionHdPrice.toString()
                    .isNotEmpty() -> searchItem.collectionHdPrice.toString()
                searchItem.trackHdPrice.toString()
                    .isNotEmpty() -> searchItem.trackHdPrice.toString()
                else -> "Price Not Found"
            }
            searchItemDate.text = searchItem.releaseDate
        }
    }
}