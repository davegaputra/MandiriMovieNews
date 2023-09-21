package com.example.mandirimovienews.model
import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("results") val results: List<Trailer>
)
