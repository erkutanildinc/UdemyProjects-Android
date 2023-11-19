package com.example.cleancomposemovieapp.data.repository

import com.example.cleancomposemovieapp.data.remote.MovieAPI
import com.example.cleancomposemovieapp.data.remote.dto.MovieDetailDto
import com.example.cleancomposemovieapp.data.remote.dto.MoviesDto
import com.example.cleancomposemovieapp.domain.repository.MovieRepository
import java.lang.reflect.Constructor
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api : MovieAPI) : MovieRepository {

    override suspend fun getMovies(search: String): MoviesDto {
        return api.getMovies(searchString = search)
    }

    override suspend fun getMovieDetail(imdbId: String): MovieDetailDto {
        return api.getMovieDetail(imdbId = imdbId)
    }
}