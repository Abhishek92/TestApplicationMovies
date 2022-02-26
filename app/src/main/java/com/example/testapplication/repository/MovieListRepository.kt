package com.example.testapplication.repository

import com.example.testapplication.network.*
import com.example.testapplication.network.Network.suspendSafeExecute
import javax.inject.Inject

interface MovieListRepository {
    suspend fun getMovieList(params: InputParams): Result<MovieList>
}

class MovieListRepositoryImpl @Inject constructor(
    private val movieListService: MovieListService
) : MovieListRepository {

    override suspend fun getMovieList(params: InputParams): Result<MovieList> {
        val response = suspendSafeExecute {
            movieListService.getMovieList(params.page)
        }

        val resultBody = response?.body()
        return if (resultBody != null && response.isSuccessful) {
            Success(resultBody)
        } else {
            if (response?.code() == 500) {
                Failure(response.code(), "Internal server error")
            } else {
                Failure(ErrorCode.ERR_NO_SERVER_RESPONSE, "No response from server, may be internet connection is not stable")
            }
        }
    }
}

