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
import kotlinx.coroutines.launch
import org.jsoup.Jsoup


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
                detailViewModel.state.observe(viewLifecycleOwner) { state ->
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
                        val price =
                            item.trackPrice ?: item.collectionPrice ?: Constant.DEFAULT_PRICE
                        val priceText = "$price \t ${item.currency}"
                        itemPrice.text = priceText

                        val description = item.longDescription ?: item.description
                        if (description.isNullOrBlank()) {
                            descriptionLayout.visibility = View.GONE
                        } else {
                            itemDescription.text = Jsoup.parse(description).text()
                        }

                        if (item.artistName.isNullOrBlank()) {
                            artistLayout.visibility = View.GONE
                        } else {
                            itemArtist.text = item.artistName
                        }

                        if (item.primaryGenreName.isNullOrBlank()) {
                            genresLayout.visibility = View.GONE
                        } else {
                            itemGenres.text = item.primaryGenreName
                        }
                    }

                }
            }
        }
    }
}