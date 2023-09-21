package com.example.mandirimovienews.model
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Review>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int,
)
