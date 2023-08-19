package com.example.tvmovies.data.service

import com.example.tvmovies.data.model.TvShowsResponse
import com.example.tvmovies.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getTVShows() : Response<TvShowsResponse>
}