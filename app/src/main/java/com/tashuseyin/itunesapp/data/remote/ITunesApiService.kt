package com.tashuseyin.itunesapp.data.remote

import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.data.remote.dto.ResultDto
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ITunesApiService {

    @GET(Constant.SEARCH_URL)
    suspend fun getSearchApi(@QueryMap queries: Map<String, String>): ResultDto
}
