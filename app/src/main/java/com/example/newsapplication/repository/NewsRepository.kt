package com.example.newsapplication.repository

import com.example.newsapplication.api.RetrofitInstance

class NewsRepository {
suspend fun getFrenchNews(countryCode : String, pageNumber : Int) = RetrofitInstance.api.getFrenchNews(countryCode,pageNumber)
}