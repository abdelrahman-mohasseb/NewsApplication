package com.example.newsapplication.repository

import com.example.newsapplication.api.NewsAPI
import com.example.newsapplication.model.NewsApiResponse
import com.example.newsapplication.util.Resource
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

// Default repository for the application
 class DefaultNewsRepository @Inject constructor(
    private val newsApi : NewsAPI
) : NewsRepository {
    override suspend fun getFrenchNews(countryCode : String, pageNumber : Int) : Resource<NewsApiResponse> {
         return try {
             val response = newsApi.getFrenchNews(countryCode,pageNumber)
             val result =response.body()
             if (response.isSuccessful && result !=null) {
                 Resource.Success(result)
             } else {
                 Resource.Error(response.message())
             }
         } catch(t: Throwable) {
             when(t) {
                 is IOException -> Resource.Error("Network Failure")
                 else -> Resource.Error("Conversion Error")
             }
         }

    }
}