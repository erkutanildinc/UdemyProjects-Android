package com.example.cleancomposemovieapp.domain.model

import com.example.cleancomposemovieapp.data.remote.dto.Rating

data class MovieDetail(
    val Actors: String,
    val Country: String,
    val Director: String,
    val Genre: String,
    val Language: String,
    val Poster: String,
    val Rated: String,
    val Released: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbRating: String,
)
