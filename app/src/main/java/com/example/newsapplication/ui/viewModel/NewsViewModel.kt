package com.example.newsapplication.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.model.NewsApiResponse
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.util.DispatcherProvider
import com.example.newsapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    // here we add to the constructor repository and dispatcher interface to define the one we need in case we will choose special ones when testing
    private val newsRepository: NewsRepository,
    private val dispatchers :  DispatcherProvider
) : ViewModel() {

    private val _frenchNews : MutableLiveData<Resource<NewsApiResponse>> = MutableLiveData()
    val frenchNews : LiveData<Resource<NewsApiResponse>> = _frenchNews
    var frenchNewsPage = 1
    var frenchNewsResponse: NewsApiResponse? = null


    init {
        getFrenchNews("fr")
    }

    fun getFrenchNews(countryCode: String) = viewModelScope.launch(dispatchers.io) {

        _frenchNews.postValue(Resource.Loading())

        when (val response = newsRepository.getFrenchNews(countryCode, frenchNewsPage)) {
            is Resource.Error ->  _frenchNews.postValue(Resource.Error(response.message!!))
            is Resource.Success -> {
                frenchNewsPage++
                // set the frenchNewsResponse to resultResponse for the first time
                if (frenchNewsResponse == null) {
                    frenchNewsResponse = response.data!!
                } else {
                    // get the old articles from frenchNewsResponse and add to them the new articles from the result response of the server
                    val oldArticles = frenchNewsResponse?.articles
                    val newArticles = response.data!!.articles
                    oldArticles?.addAll(newArticles)
                }
                _frenchNews.postValue(Resource.Success(frenchNewsResponse ?: response.data!!))
            }

        }
    }

}

