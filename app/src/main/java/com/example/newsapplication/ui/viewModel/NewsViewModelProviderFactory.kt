package com.example.newsapplication.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.repository.DefaultNewsRepository


/*  val app: Application,
class NewsViewModelProviderFactory(
  private val newsRepository: DefaultNewsRepository
):ViewModelProvider.Factory {


  override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return NewsViewModel(app,newsRepository) as T
  }
}*/