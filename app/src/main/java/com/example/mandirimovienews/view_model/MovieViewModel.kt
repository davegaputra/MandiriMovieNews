package com.example.mandirimovienews.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mandirimovienews.model.Genre
import com.example.mandirimovienews.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.mandirimovienews.repository.MovieRepository
import com.example.mandirimovienews.util.Resource
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {
    private val _popular = MutableLiveData<Resource<List<Movie>>>()
    private val _upcoming = MutableLiveData<Resource<List<Movie>>>()
    private val _topRated = MutableLiveData<Resource<List<Movie>>>()
    private val _genre = MutableLiveData<Resource<List<Genre>>>()
    val popular: LiveData<Resource<List<Movie>>> get() = _popular
    val upcoming: LiveData<Resource<List<Movie>>> get() = _upcoming
    val topRated: LiveData<Resource<List<Movie>>> get() = _topRated
    val genre: LiveData<Resource<List<Genre>>> get() = _genre

    fun getPopular(page: Int) = viewModelScope.launch {
        _popular.postValue(Resource.Loading())
        try {
            val response = repository.getPopular(page)
            _popular.postValue(Resource.Success(response.results))
        } catch (e: IOException) {
            _popular.postValue(Resource.Error(e.message))
        }
    }

    fun getUpcoming(page: Int) = viewModelScope.launch {
        _upcoming.postValue(Resource.Loading())
        try {
            val response = repository.getUpcoming(page)
            _upcoming.postValue(Resource.Success(response.results))
        } catch (e: IOException) {
            _upcoming.postValue(Resource.Error(e.message))
        }
    }

    fun getTopRated(page: Int) = viewModelScope.launch {
        _topRated.postValue(Resource.Loading())
        try {
            val response = repository.getTopRated(page)
            _topRated.postValue(Resource.Success(response.results))
        } catch (e: IOException) {
            _topRated.postValue(Resource.Error(e.message))
        }
    }

    fun getGenre() = viewModelScope.launch {
        _genre.postValue(Resource.Loading())
        try {
            val response = repository.getGenre()
            _genre.postValue(Resource.Success(response.genres))
        } catch (e: IOException) {
            _genre.postValue(Resource.Error(e.message))
        }
    }
}