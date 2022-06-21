package com.tashuseyin.itunesapp.domain.model

import android.os.Parcelable
import com.tashuseyin.itunesapp.common.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectMediaType(
    val mediaType: String = Constant.DEFAULT_MEDIA_TYPE,
    val mediaTypeId: Int = 0
):Parcelable
