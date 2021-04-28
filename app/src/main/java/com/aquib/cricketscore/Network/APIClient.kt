package com.aquib.cricketscore.Network

import com.aquib.cricketscore.ApiResponse.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query


interface APIClient {
    @GET("top-headlines")
    fun getSportsHeadlines(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call <NewsData?>?

    @GET("everything")
    fun getNews(
        @Query("q") query: String?,
        @Query("apiKey") apiKey: String?
    ): Call <NewsData?>?
}
