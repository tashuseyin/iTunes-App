package com.tashuseyin.itunesapp.presentation.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.itunesapp.common.extension.loadImageView
import com.tashuseyin.itunesapp.common.extension.placeholderProgressBar
import com.tashuseyin.itunesapp.databinding.SearchItemRowBinding
import com.tashuseyin.itunesapp.domain.model.SearchItem

class SearchViewHolder(private val binding: SearchItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(searchItem: SearchItem, onItemClickListener: ((SearchItem) -> Unit)? = null) {
        binding.apply {
            searchItemImage.loadImageView(
                searchItem.artworkUrl100,
                placeholderProgressBar(searchItemImage.context)
            )
            searchItemTitle.text = searchItem.trackName ?: searchItem.collectionName
            searchItemWrapperType.text = searchItem.wrapperType

            searchItemConstraint.setOnClickListener {
                onItemClickListener?.invoke(searchItem)
            }
        }

    }
}
