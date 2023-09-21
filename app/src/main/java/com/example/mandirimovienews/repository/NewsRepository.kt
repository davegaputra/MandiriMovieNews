package com.example.mandirimovienews.repository

import androidx.paging.PagingData
import com.example.mandirimovienews.model.Article
import com.example.mandirimovienews.model.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(category: String, page: Int): NewsResponse
    suspend fun getSearchNews(keyword: String): Flow<PagingData<Article>>
}