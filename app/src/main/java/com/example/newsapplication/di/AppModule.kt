package com.example.newsapplication.di

import com.example.newsapplication.BuildConfig
import com.example.newsapplication.api.NewsAPI
import com.example.newsapplication.repository.DefaultNewsRepository
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.util.Constants
import com.example.newsapplication.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

        @Singleton
        @Provides
        fun provideNewsApplicationAPI(): NewsAPI {
            // logging interceptor is useful for debug to see the api responses
            val httpClient =
                if (BuildConfig.DEBUG){
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build()
                }else{
                    OkHttpClient
                        .Builder()
                        .build()
                }

           return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
               .create(NewsAPI::class.java)
        }

        @Singleton
        @Provides
        fun provideMainRepository(api : NewsAPI): NewsRepository = DefaultNewsRepository(api)


        @Singleton
        @Provides
        fun provideDispatchers(api : NewsAPI): DispatcherProvider = object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
            override val unconfined: CoroutineDispatcher
                get() = Dispatchers.Unconfined

        }

}
