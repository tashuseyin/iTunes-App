package com.tashuseyin.itunesapp.presentation.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.itunesapp.common.extension.loadImageView
import com.tashuseyin.itunesapp.common.extension.placeholderProgressBar
import com.tashuseyin.itunesapp.databinding.SearchItemRowBinding
import com.tashuseyin.itunesapp.domain.model.SearchItem

class SearchViewHolder(private val binding: SearchItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(searchItem: SearchItem, onItemClickListener: ((String) -> Unit)? = null) {
        binding.apply {
            searchItemImage.loadImageView(
                searchItem.artworkUrl100,
                placeholderProgressBar(searchItemImage.context)
            )
            if (searchItem.trackName!!.isNotBlank()) {
                searchItemTitle.text = searchItem.trackName
            } else {
                searchItemTitle.text = searchItem.collectionName
            }

            searchItemWrapperType.text = searchItem.wrapperType


            searchItemConstraint.setOnClickListener {
                val id = searchItem.trackId ?: searchItem.collectionId
                onItemClickListener?.invoke(id.toString())
            }
        }

    }
}
