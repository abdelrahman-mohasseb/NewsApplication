package com.example.newsapplication.repository

import com.example.newsapplication.model.NewsApiResponse
import com.example.newsapplication.util.Resource
import retrofit2.Response


    // the inteface can be used for the tests (fake repository) or for the application (Default repository)
interface NewsRepository {
    suspend fun getFrenchNews(countryCode : String, pageNumber : Int): Resource<NewsApiResponse>
}