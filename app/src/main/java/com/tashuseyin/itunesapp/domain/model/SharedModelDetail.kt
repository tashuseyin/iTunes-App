package com.tashuseyin.itunesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SharedModelDetail(
    val trackId: String?,
    val collectionId: String?,
    val wrapperType: String?
) : Parcelable