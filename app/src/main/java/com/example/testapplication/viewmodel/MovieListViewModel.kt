package com.example.testapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.testapplication.network.*
import com.example.testapplication.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieListRepository: MovieListRepository) :
    ViewModel() {

    fun getMovieList(params: InputParams): LiveData<Result<MovieList>> {
        return liveData(Dispatchers.IO) {
            emit(Loading("Loading.."))
            when (val result = movieListRepository.getMovieList(params)) {
                is Success -> emit(Success(result.data))
                is Failure -> emit(result)
            }
        }
    }
}