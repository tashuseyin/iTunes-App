package com.tashuseyin.itunesapp.domain.model

data class SearchItem(
    val artistName: String?,
    val artworkUrl100: String?,
    val artworkUrl600: String?,
    val collectionId: Int?,
    val collectionName: String?,
    val collectionPrice: Double?,
    val country: String?,
    val currency: String?,
    val description: String?,
    val longDescription: String?,
    val primaryGenreName: String?,
    val releaseDate: String?,
    val trackId: Int?,
    val trackName: String?,
    val trackPrice: Double?,
    val wrapperType: String?
)