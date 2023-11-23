package com.example.cleancomposemovieapp.presentation.movie_list

sealed class MovieListEvent {
        data class Search(val searchString : String) : MovieListEvent()
}