package com.example.newsapplication.api

import com.example.newsapplication.model.NewsApiResponse
import com.example.newsapplication.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getFrenchNews(
        @Query("country")
        countryCode: String = "fr",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsApiResponse>
}