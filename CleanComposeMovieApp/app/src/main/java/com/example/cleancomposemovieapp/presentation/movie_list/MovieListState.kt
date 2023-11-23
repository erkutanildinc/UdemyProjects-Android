package com.example.cleancomposemovieapp.presentation.movie_list

import com.example.cleancomposemovieapp.domain.model.Movie

data class MovieListState(
  val isLoading : Boolean = false,
  val movies : List<Movie> = emptyList(),
  val error : String = "",
  val search : String = "Martian"

)