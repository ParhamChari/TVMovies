package com.example.tvmovies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmovies.data.event.ItemTvShowClickListener
import com.example.tvmovies.data.model.TvShowsItem
import com.example.tvmovies.databinding.TvshowItemBinding

class TvShowsAdapter(val listener: ItemTvShowClickListener) : RecyclerView.Adapter<TvShowsViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<TvShowsItem>() {

        override fun areItemsTheSame(oldItem: TvShowsItem, newItem: TvShowsItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TvShowsItem, newItem: TvShowsItem): Boolean =
            oldItem.id == newItem.id

    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    var tvShowModel : List<TvShowsItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder =
        TvShowsViewHolder(
            TvshowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) =
        holder.bindViews(tvShowModel[position], listener)


    override fun getItemCount(): Int = tvShowModel.size
}