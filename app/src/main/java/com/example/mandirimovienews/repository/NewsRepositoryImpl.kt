package com.example.mandirimovienews.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mandirimovienews.api.NewsAPIs
import com.example.mandirimovienews.model.Article
import com.example.mandirimovienews.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import com.example.mandirimovienews.paging.SearchNewsPagingSource
import com.example.mandirimovienews.util.Const
import okio.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsAPIs,
) : NewsRepository {

    override suspend fun getTopHeadlines(category: String, page: Int): NewsResponse {
        val response = api.getTopHeadlines(
            api_key = Const.API_KEY_NEWS,
            country = "us",
            category = category,
            language = "en",
            page = page,
            pageSize = 20,
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw IOException("Data Retrieval Failed")
        }
    }

    override suspend fun getSearchNews(keyword: String): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 200
        ),
        pagingSourceFactory = {
            SearchNewsPagingSource(api, keyword)
        }
    ).flow
}