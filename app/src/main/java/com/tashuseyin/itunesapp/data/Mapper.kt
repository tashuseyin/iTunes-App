package com.tashuseyin.itunesapp.data

import com.tashuseyin.itunesapp.data.remote.dto.SearchResultDto
import com.tashuseyin.itunesapp.domain.model.SearchItem

fun SearchResultDto.toDomain(): SearchItem {
    return SearchItem(
        artistId = artistId,
        artistName = artistName,
        artistViewUrl = artistViewUrl,
        artworkUrl100 = artworkUrl100,
        artworkUrl30 = artworkUrl30,
        artworkUrl60 = artworkUrl60,
        artworkUrl600 = artworkUrl600,
        collectionArtistId = collectionArtistId,
        collectionArtistName = collectionArtistName,
        collectionArtistViewUrl = collectionArtistViewUrl,
        collectionCensoredName = collectionCensoredName,
        collectionExplicitness = collectionExplicitness,
        collectionHdPrice = collectionHdPrice,
        collectionId = collectionId,
        collectionName = collectionName,
        collectionPrice = collectionPrice,
        collectionViewUrl = collectionViewUrl,
        contentAdvisoryRating = contentAdvisoryRating,
        copyright = copyright,
        country = country,
        currency = currency,
        description = description,
        discCount = discCount,
        discNumber = discNumber,
        feedUrl = feedUrl,
        genreIds = genreIds,
        genres = genres,
        hasITunesExtras = hasITunesExtras,
        isStreamable = isStreamable,
        kind = kind,
        longDescription = longDescription,
        previewUrl = previewUrl,
        primaryGenreName = primaryGenreName,
        releaseDate = releaseDate,
        shortDescription = shortDescription,
        trackCensoredName = trackCensoredName,
        trackCount = trackCount,
        trackExplicitness = trackExplicitness,
        trackHdPrice = trackHdPrice,
        trackHdRentalPrice = trackHdRentalPrice,
        trackId = trackId,
        trackName = trackName,
        trackNumber = trackNumber,
        trackPrice = trackPrice,
        trackRentalPrice = trackRentalPrice,
        trackTimeMillis = trackTimeMillis,
        trackViewUrl = trackViewUrl,
        wrapperType = wrapperType
    )
}