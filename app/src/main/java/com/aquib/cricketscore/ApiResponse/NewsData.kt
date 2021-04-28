package com.aquib.cricketscore.ApiResponse


import com.google.gson.annotations.SerializedName

data class NewsData(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)