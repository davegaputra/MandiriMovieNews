package com.example.mandirimovienews.paging

import com.example.mandirimovienews.api.MovieAPIs
import com.example.mandirimovienews.model.Movie
import com.example.mandirimovienews.util.Const
import okio.IOException
import retrofit2.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingState


class DiscoverMovieByGenrePagingSource(private val api: MovieAPIs, private val genre_id: String) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page: Int = params.key ?: FIRST_PAGE_INDEX
            val response = api.getMovieByGenre(
                api_key = Const.API_KEY_MOVIE,
                page = page,
                genre_id = genre_id
            )
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!.results,
                    prevKey = if (page == FIRST_PAGE_INDEX) null else page - 1,
                    nextKey = if (response.body()?.total_pages!! <= page) null else page + 1
                )
            } else {
                throw IOException("Failed fetch data")
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}