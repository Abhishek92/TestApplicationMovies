package com.example.testapplication.network

import com.google.gson.annotations.SerializedName
import java.io.IOException
import java.lang.reflect.UndeclaredThrowableException


data class MovieList (

    @SerializedName("page") val page : Int,
    @SerializedName("results") val results : List<Movie>,
    @SerializedName("total_pages") val total_pages : Int,
    @SerializedName("total_results") val total_results : Int
)

data class Movie (
    @SerializedName("adult") val adult : Boolean,
    @SerializedName("backdrop_path") val backdrop_path : String,
    @SerializedName("genre_ids") val genre_ids : List<Int>,
    @SerializedName("id") val id : Int,
    @SerializedName("original_language") val original_language : String,
    @SerializedName("original_title") val original_title : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("title") val title : String,
    @SerializedName("video") val video : Boolean,
    @SerializedName("vote_average") val vote_average : Double,
    @SerializedName("vote_count") val vote_count : Int
)

data class InputParams(
    val page: Int,
)

sealed class Result<out R : Any>

data class Success<out R : Any>(val data: R, val message: String = "") : Result<R>()

data class Failure(val errorCode: Int, val message: String) : Result<Nothing>()

data class Loading(val state: String? = null) : Result<Nothing>()

object Network {
    suspend fun <T> suspendSafeExecute(block: suspend () -> T): T? {
        return try {
            block()
        } catch (e: IOException) {
            null
        } catch (e: UndeclaredThrowableException) {
            null
        }
    }
}

object ErrorCode {
   const val ERR_NO_SERVER_RESPONSE = 103
}

