package com.example.mandirimovienews.repository

import androidx.paging.PagingData
import com.example.mandirimovienews.model.*
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    suspend fun getPopular(page: Int) : MovieResponse
    suspend fun getTopRated(page: Int) : MovieResponse
    suspend fun getUpcoming(page: Int) : MovieResponse
    suspend fun getGenre() : GenreResponse
    suspend fun getMovie(movie_id: String) : MovieDetail
    suspend fun getTrailer(movie_id: String) : TrailerResponse
    suspend fun getReviews(movie_id: String) : Flow<PagingData<Review>>
    suspend fun getDiscoverMovieByGenre(genre_id: String): Flow<PagingData<Movie>>
    suspend fun getSearchMovie(keyword: String): Flow<PagingData<Movie>>
}