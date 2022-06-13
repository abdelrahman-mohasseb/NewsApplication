package com.example.newsapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsapplication.R
import com.example.newsapplication.repository.DefaultNewsRepository
import com.example.newsapplication.ui.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val newsRepository = DefaultNewsRepository()
       // val viewModelProviderFactory = NewsViewModelProviderFactory(application,newsRepository)
       // viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        // connect the bottom navigation view with the navigation components
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        val navController= navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

    }



}