package com.example.cleancomposemovieapp.domain.repository

import com.example.cleancomposemovieapp.data.remote.dto.MovieDetailDto
import com.example.cleancomposemovieapp.data.remote.dto.MoviesDto

interface MovieRepository {


    suspend fun getMovies(search : String) : MoviesDto

    suspend fun getMovieDetail(imdbId : String) : MovieDetailDto

}