package com.tashuseyin.itunesapp.data

import com.tashuseyin.itunesapp.data.remote.dto.BaseResultItemDto
import com.tashuseyin.itunesapp.domain.model.SearchItem

fun BaseResultItemDto.toDomain(): SearchItem {
    return SearchItem(
        artistName = artistName,
        artworkUrl100 = artworkUrl100,
        artworkUrl600 = artworkUrl600,
        collectionId = collectionId,
        collectionName = collectionName,
        collectionPrice = collectionPrice,
        country = country,
        currency = currency,
        description = description,
        longDescription = longDescription,
        primaryGenreName = primaryGenreName,
        releaseDate = releaseDate,
        trackId = trackId,
        trackName = trackName,
        trackPrice = trackPrice,
        wrapperType = wrapperType
    )
}