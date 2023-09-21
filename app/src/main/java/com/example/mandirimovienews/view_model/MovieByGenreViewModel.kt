package com.example.mandirimovienews.view_model

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mandirimovienews.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import com.example.mandirimovienews.repository.MovieRepository
import com.example.mandirimovienews.util.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MovieByGenreViewModel @Inject constructor(
    private val repository: MovieRepository,
) : BaseViewModel() {
    private lateinit var _movieFlow: Flow<PagingData<Movie>>
    val movieFlow: Flow<PagingData<Movie>> get() = _movieFlow

    fun getDiscoverMovieByGenre(genre_id: String) = launchPagingAsync({
        repository.getDiscoverMovieByGenre(genre_id).cachedIn(viewModelScope)
    }, {
        _movieFlow = it
    })
}