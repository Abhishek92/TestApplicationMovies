package com.example.testapplication.network

import com.example.testapplication.di.API_KEY
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListService {

    @GET("?api_key=$API_KEY")
    @JvmSuppressWildcards
    suspend fun getMovieList(@Query("page") page: Int): Response<MovieList>

    companion object {
        fun createService(retrofit: Retrofit): MovieListService =
            retrofit.create(MovieListService::class.java)
    }
}