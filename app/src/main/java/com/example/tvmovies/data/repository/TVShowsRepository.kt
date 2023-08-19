package com.example.tvmovies.data.repository

import com.example.tvmovies.data.service.ApiService
import javax.inject.Inject

class TVShowsRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getTvShows() = apiService.getTVShows()
}