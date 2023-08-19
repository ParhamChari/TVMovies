package com.example.tvmovies.ui.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.tvmovies.data.event.ItemTvShowClickListener
import com.example.tvmovies.data.model.TvShowsItem
import com.example.tvmovies.databinding.TvshowItemBinding

class TvShowsViewHolder(private val binding: TvshowItemBinding) : ViewHolder(binding.root){

    fun bindViews(tvShow: TvShowsItem, listener : ItemTvShowClickListener) {

        binding.apply {
            movieImage.load(tvShow.image.original) {
                crossfade(true)
                crossfade(1000)
            }
            movieName.text = tvShow.name

            movieItem.setOnClickListener {
                listener.onItemClick(tvShow)
            }

        }
    }
}