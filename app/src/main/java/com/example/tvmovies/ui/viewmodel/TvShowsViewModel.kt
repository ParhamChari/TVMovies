package com.example.tvmovies.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvmovies.data.model.TvShowsItem
import com.example.tvmovies.data.repository.TVShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(private val repository: TVShowsRepository) : ViewModel() {

    private val _response = MutableLiveData<List<TvShowsItem>>()
    val responseTvShows : LiveData<List<TvShowsItem>>
        get() = _response

    init {
        getAllTvShows()
    }

    private fun getAllTvShows() = viewModelScope.launch {
        repository.getTvShows().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body()!!)
            } else {
                Log.d("error", "getAll tv shows Error:${response.code()}")
            }
        }
    }
}