package com.example.cleancomposemovieapp.data.remote

import com.example.cleancomposemovieapp.data.remote.dto.MovieDetailDto
import com.example.cleancomposemovieapp.data.remote.dto.MoviesDto
import com.example.cleancomposemovieapp.util.Constants.API_KEY
import com.example.cleancomposemovieapp.util.Resource
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    //https://www.omdbapi.com/?apikey=89ed8035&t=batman
    //https://www.omdbapi.com/?apikey=89ed8035&i=tt1877830

    @GET(".")
    suspend fun getMovies(
        @Query("s") searchString : String,
        @Query("apikey") apiKey : String = API_KEY
    ) : MoviesDto

    @GET(".")
    suspend fun getMovieDetail(
        @Query("i") imdbId : String,
        @Query("apikey") apiKey : String = API_KEY
    ) : MovieDetailDto

}