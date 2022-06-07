package com.example.newsapplication.ui.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.NewsApplication
import com.example.newsapplication.model.NewsApiResponse
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    app: Application,
    val newsRepository: NewsRepository
) : AndroidViewModel(app) {

    val frenchNews : MutableLiveData<Resource<NewsApiResponse>> = MutableLiveData()
    var frenchNewsPage = 1
    var frenchNewsResponse: NewsApiResponse? = null


    init {
        getFrenchNews("fr")
    }

    fun getFrenchNews(countryCode: String) = viewModelScope.launch {
        frenchNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.getFrenchNews(countryCode, frenchNewsPage)
                frenchNews.postValue(handleFrenchNewsResponse(response))
            } else {
                frenchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> frenchNews.postValue(Resource.Error("Network Failure"))
                else -> frenchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun handleFrenchNewsResponse(response: Response<NewsApiResponse>) : Resource<NewsApiResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                frenchNewsPage++
                // set the frenchNewsResponse to resultResponse for the first time
                if(frenchNewsResponse == null) {
                    frenchNewsResponse = resultResponse
                } else {
                    // get the old articles from frenchNewsResponse and add to them the new articles from the result response of the server
                    val oldArticles = frenchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(frenchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {
        // we used the application context instead of activity context to respect the MVVM architecture
        // and to make sure that we check connection as long as the application is nit killed
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}

