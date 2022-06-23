package com.tashuseyin.itunesapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.common.extension.dateToString
import com.tashuseyin.itunesapp.common.extension.loadImageView
import com.tashuseyin.itunesapp.common.extension.placeholderProgressBar
import com.tashuseyin.itunesapp.databinding.FragmentDetailBinding
import com.tashuseyin.itunesapp.presentation.binding_adapter.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : BindingFragment<FragmentDetailBinding>() {
    private val detailViewModel: DetailViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUI()
    }

    private fun observeUI() {
        binding.apply {
            lifecycleScope.launch {
                detailViewModel.state.collect { state ->
                    progressBar.isVisible = state.isLoading
                    scrollView.isVisible = state.isScrollView
                    if (state.error.isNotBlank()) {
                        errorText.text = state.error
                    }
                    state.detailItem?.let { item ->
                        itemDetailImage.loadImageView(
                            item.artworkUrl100 ?: item.artworkUrl600,
                            placeholderProgressBar(requireContext())
                        )
                        itemYear.text = dateToString(item.releaseDate!!)
                        itemName.text = item.trackName ?: item.collectionName
                        itemCountry.text = item.country ?: Constant.DEFAULT_COUNTRY
                        val price = (item.trackPrice ?: 0.0).toString() + item.currency
                        itemPrice.text = price

                        itemDescription.text = item.longDescription ?: item.description

                        itemArtist.text = item.artistName
                        itemGenres.text = item.primaryGenreName
                    }

                }
            }
        }
    }
}