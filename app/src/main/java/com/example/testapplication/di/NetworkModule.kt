package com.example.testapplication.di

import androidx.annotation.NonNull
import androidx.viewbinding.BuildConfig
import com.example.testapplication.network.MovieListService
import com.example.testapplication.repository.MovieListRepository
import com.example.testapplication.repository.MovieListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val API_URL = "https://api.themoviedb.org/3/movie/popular/"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val API_KEY = "38a73d59546aa378980a88b645f487fc"

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        @Provides
        @NonNull
        @Singleton
        fun provideRestClient(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(30, TimeUnit.SECONDS)
            httpClient.connectTimeout(30, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logging)
            }
            val client = httpClient.build()
            val builder = Retrofit.Builder()
            builder.baseUrl(API_URL)
            builder.addConverterFactory(GsonConverterFactory.create())
            builder.client(client)
            return builder.build()
        }

        @Provides
        @NonNull
        fun provideMovieListService(retrofit: Retrofit): MovieListService {
            return MovieListService.createService(retrofit)
        }
    }

    @Binds
    @NonNull
    abstract fun provideMovieListRepository(movieListRepository: MovieListRepositoryImpl): MovieListRepository
}