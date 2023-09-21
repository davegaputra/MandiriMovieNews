package com.example.mandirimovienews.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import com.example.mandirimovienews.api.MovieAPIs
import com.example.mandirimovienews.model.*
import com.example.mandirimovienews.paging.DiscoverMovieByGenrePagingSource
import com.example.mandirimovienews.paging.ReviewMoviePagingSource
import com.example.mandirimovienews.paging.SearchMoviePagingSource
import com.example.mandirimovienews.util.Const
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieAPIs,
) : MovieRepository {

    override suspend fun getPopular(page: Int) : MovieResponse {
        val response = api.getPopularMovie(
            api_key = Const.API_KEY_MOVIE,
            page = page
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw IOException("Data Retrieval Failed")
        }
    }

    override suspend fun getTopRated(page: Int) : MovieResponse {
        val response = api.getTopRatedMovie(
            api_key = Const.API_KEY_MOVIE,
            page = page
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw IOException("Data Retrieval Failed")
        }
    }

    override suspend fun getUpcoming(page: Int) : MovieResponse {
        val response = api.getUpcomingMovie(
            api_key = Const.API_KEY_MOVIE,
            page = page
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw IOException("Data Retrieval Failed")
        }
    }

    override suspend fun getGenre() : GenreResponse {
        val response = api.getGenreMovie(
            api_key = Const.API_KEY_MOVIE,
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw IOException("Data Retrieval Failed")
        }
    }

    override suspend fun getMovie(movie_id: String) : MovieDetail {
        val response = api.getMovieDetail(
            api_key = Const.API_KEY_MOVIE,
            movie_id = movie_id
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw IOException("Data Retrieval Failed")
        }
    }

    override suspend fun getTrailer(movie_id: String) : TrailerResponse {
        val response = api.getTrailerLink(
            api_key = Const.API_KEY_MOVIE,
            movie_id = movie_id
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw IOException("Data Retrieval Failed")
        }
    }

    override suspend fun getReviews(movie_id: String): Flow<PagingData<Review>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 200
        ),
        pagingSourceFactory = {
            ReviewMoviePagingSource(api, movie_id)
        }
    ).flow

    override suspend fun getDiscoverMovieByGenre(genre_id: String): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 200
        ),
        pagingSourceFactory = {
            DiscoverMovieByGenrePagingSource(api, genre_id)
        }
    ).flow

    override suspend fun getSearchMovie(keyword: String): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 200
        ),
        pagingSourceFactory = {
            SearchMoviePagingSource(api, keyword)
        }
    ).flow

}