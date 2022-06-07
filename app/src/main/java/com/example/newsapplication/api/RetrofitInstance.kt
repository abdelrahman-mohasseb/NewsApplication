package com.example.newsapplication.api

import com.example.newsapplication.BuildConfig
import com.example.newsapplication.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
      // lazy used to initialize our object once
        private val retrofit by lazy {
          // logging interceptor is useful for debug to see the api responses
          val httpClient =
          if (BuildConfig.DEBUG){
              val loggingInterceptor =HttpLoggingInterceptor()
              loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
             OkHttpClient.Builder()
                  .addInterceptor(loggingInterceptor)
                  .build()
          }else{
              OkHttpClient
                  .Builder()
                  .build()
          }

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }

        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}