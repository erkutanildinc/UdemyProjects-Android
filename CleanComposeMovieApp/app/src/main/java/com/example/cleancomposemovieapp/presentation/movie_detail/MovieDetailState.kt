package com.example.cleancomposemovieapp.presentation.movie_detail

import com.example.cleancomposemovieapp.domain.model.Movie
import com.example.cleancomposemovieapp.domain.model.MovieDetail

data class MovieDetailState (
    val isLoading : Boolean = false,
    val movie : MovieDetail? = null,
    val error : String = ""
)