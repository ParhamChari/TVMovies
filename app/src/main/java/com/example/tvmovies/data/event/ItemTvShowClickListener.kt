package com.example.tvmovies.data.event

import com.example.tvmovies.data.model.TvShowsItem

interface ItemTvShowClickListener {
    fun onItemClick(tvShow : TvShowsItem)
}