package com.example.mandirimovienews.api
import com.example.mandirimovienews.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIs {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") api_key: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun getSearchNews(
        @Query("apiKey") api_key: String,
        @Query("q") keyword: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): Response<NewsResponse>

}